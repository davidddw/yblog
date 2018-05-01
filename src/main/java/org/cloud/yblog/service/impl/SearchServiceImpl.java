/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.cloud.yblog.service.impl;

import static org.cloud.yblog.constant.LuceneFieldConstant.CONTENT;
import static org.cloud.yblog.constant.LuceneFieldConstant.ID;
import static org.cloud.yblog.constant.LuceneFieldConstant.STOREDID;
import static org.cloud.yblog.constant.LuceneFieldConstant.TITLE;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.cloud.yblog.dto.SearchResult;
import org.cloud.yblog.service.ISearchService;
import org.lionsoul.jcseg.analyzer.JcsegAnalyzer;
import org.lionsoul.jcseg.tokenizer.core.JcsegTaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by d05660ddw on 2017/3/19.
 */
public class SearchServiceImpl implements ISearchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Analyzer analyzer;

    private Directory directory;

    private IndexWriterConfig indexWriterConfig;

    private IndexWriter indexWriter;

    @PostConstruct
    public void init() throws IOException {
        logger.info("Start init lucene service");
        analyzer = new JcsegAnalyzer(JcsegTaskConfig.COMPLEX_MODE);
        //1、创建Directory. 在硬盘上生成Directory
        directory = FSDirectory.open(Paths.get("/tmp/testindex"));
        //2、创建IndexWriter
        indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(directory, indexWriterConfig);
        logger.info("Lucene inited success");
    }

    @PreDestroy
    public void destroy() throws IOException {
        logger.info("start destroy lucene service");
        //6、使用完成后需要将writer进行关闭
        indexWriter.close();
        logger.info("successfully closed");
    }

    /**
     * 建立索引
     * @param title
     * @param content
     * @param id
     */
    public void addSource(String title, String content, int id) {
        //3、创建document对象
        Document doc = new Document();
        //4、为document添加field对象
        doc.add(new IntPoint(ID, id));
        doc.add(new StoredField(STOREDID, id));
        doc.add(new TextField(TITLE, title, Field.Store.YES));
        doc.add(new TextField(CONTENT, content, Field.Store.YES));
        try {
            //5、通过IndexWriter添加文档到索引中
            indexWriter.addDocument(doc);
            indexWriter.commit();
        } catch (IOException e) {
            logger.error("doc add failed, the title is {}, the content is {}", title, content, e);
        }
    }

    /**
     * 查询内容
     * @param qs
     * @return
     */
    public List<SearchResult> query(String qs) {
        List<SearchResult> searchResults = new ArrayList<>();
        DirectoryReader indexReader = null;
        try {
            String[] fields = {TITLE, CONTENT};
            Map<String, Float> boost = new HashMap<String, Float>() {{
                put(TITLE, 2.0f);
                put(CONTENT, 1.0f);
            }};
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer, boost);
            Query query = parser.parse(qs);
            // TODO: 2016/11/12 figure out why it need to create every time.
            //2、创建IndexReader
            indexReader = DirectoryReader.open(indexWriter);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopFieldDocs search = searcher.search(query, 1000, Sort.RELEVANCE);
            if (search.scoreDocs.length == 0) {
                return searchResults;
            }
            QueryScorer scorer = new QueryScorer(query);
            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<B>", "</B>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
            highlighter.setTextFragmenter(fragmenter);
            for (ScoreDoc scoreDoc : search.scoreDocs) {
                Document doc = null;
                try {
                    doc = searcher.doc(scoreDoc.doc);
                    SearchResult result = new SearchResult();
                    String sId = doc.get(STOREDID);
                    result.setId(Integer.valueOf(sId));
                    String title = doc.get(TITLE);
                    result.setTitle(title);

                    if (title != null) {
                        TokenStream token = analyzer.tokenStream(TITLE, new StringReader(title));
                        String bestFragment = highlighter.getBestFragment(token, title);
                        if (StringUtils.isNotEmpty(bestFragment)) {
                            result.setTitle(bestFragment);
                        }
                    }
                    String content = doc.get(CONTENT);
                    if (content != null) {
                        TokenStream token = analyzer.tokenStream(CONTENT, new StringReader(content));
                        String bestFragment = highlighter.getBestFragment(token, content);
                        if (StringUtils.isNotEmpty(bestFragment)) {
                            result.setMarkText(bestFragment);
                        }
                    }

                    searchResults.add(result);
                } catch (Exception e) {
                    logger.error("doc catch exception, the docId is {} the title is:{}", doc.get(STOREDID), doc.get(TITLE), e);
                }
            }

            return searchResults;
        } catch (Exception e) {
            logger.error("query with {} catch exception", qs, e);
            return searchResults;
        } finally {
            if (indexReader != null) {
                try {
                    indexReader.close();
                } catch (IOException e) {
                    logger.error("indexReaderClose catch exception", e);
                }
            }
        }
    }

    public void update(String title, String content, int id) {
        //3、创建document对象
        Document doc = new Document();
        //4、为document添加field对象
        doc.add(new IntPoint(ID, id));
        doc.add(new StoredField(STOREDID, id));
        doc.add(new TextField(TITLE, title, Field.Store.YES));
        doc.add(new TextField(CONTENT, content, Field.Store.YES));
        try {
            //5、删除原来的id
            delete(id);
            //6. 添加新的id
            indexWriter.addDocument(doc);
            indexWriter.commit();
        } catch (IOException e) {
            logger.error("doc add failed, the doc id is {},the title is {} ,the content is {}", id, title, content, e);
        }
    }

    public void delete(int id) {
        Query query = IntPoint.newExactQuery(ID, id);
        try {
            indexWriter.deleteDocuments(query);
            indexWriter.commit();
        } catch (IOException e) {
            logger.error("delete failed, the id is {}", id, e);
        }
    }
}
