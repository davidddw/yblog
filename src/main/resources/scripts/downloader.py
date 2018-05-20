#!/usr/bin/env python3
# -*- coding:utf-8 -*-

import requests
from urllib.parse import urlparse
from bs4 import BeautifulSoup
import re
import os.path
from const import config

URL = 'http://www.86kl.com/book/12/12454/'
CHAPTER_TMP = 'chapter_tmp.txt'
DIR_PATH = 'down'
TMP_PATH = 'tmp'

common_used_numerals_tmp = {'零': 0, '一': 1, '二': 2, '两': 2, '三': 3, '四': 4,
                            '五': 5, '六': 6, '七': 7, '八': 8, '九': 9, '十': 10,
                            '百': 100, '千': 1000, '万': 10000, '亿': 100000000}
common_used_numerals = {}
for key in common_used_numerals_tmp:
    common_used_numerals[key] = common_used_numerals_tmp[key]


def chinese2digits(uchars_chinese):
    total = 0
    r = 1  # 表示单位：个十百千...
    for i in range(len(uchars_chinese) - 1, -1, -1):
        val = common_used_numerals.get(uchars_chinese[i])
        if val >= 10 and i == 0:
            # 应对 十三 十四 十*之类
            if val > r:
                r = val
                total = total + val
            else:
                r = r * val
                # total =total + r * x
        elif val >= 10:
            if val > r:
                r = val
            else:
                r = r * val
        else:
            total = total + r * val
    return total


def get_attr(soup, tag, attr):
    if soup.select(tag):
        return soup.select(tag)[0].attrs[attr]


def get_text(soup, tag):
    return soup.select(tag)[0].text


def is_secure(url_str):
    parse_string = urlparse(url_str)
    if parse_string.scheme == 'https':
        return True
    else:
        return False


def get_host_str(url_str, host=True):
    parse_string = urlparse(url_str)
    if host:
        return '{}://{}'.format(parse_string.scheme, parse_string.netloc)
    else:
        return url_str


def get_html(url_str):
    response = requests.get(url_str, verify=is_secure(url_str))
    return response.text.encode(response.encoding)


def get_chapter(url_str, encode='utf8', host=True):
    html_string = get_html(url_str)
    soup = BeautifulSoup(html_string, 'lxml')
    target_file = os.path.abspath(os.path.join(TMP_PATH, CHAPTER_TMP))
    href_list = soup.find_all("dd")
    with open(target_file, 'w', encoding=encode) as outfile:
        for i in href_list:
            short_name = get_attr(i, 'a', 'href')
            if short_name:
                outfile.write(get_host_str(url_str, host) + short_name + '\n')
        print('Write {} successful.'.format(target_file))


def get_filename(m):
    pattern = re.compile(r'http.*/(\d*).html')
    items = re.findall(pattern, m)
    if items:
        return items[0]
    else:
        return ''


def get_content(url_str):
    html_string = get_html(url_str)
    soup = BeautifulSoup(html_string, 'lxml')
    title = soup.find_all('div', attrs={'class': config['title_div']})[0]
    title_str = get_text(title, config['title_tag'])
    content = soup.find_all(id=config['content_id'])[0]
    return '{}\n\n{}'.format(title_str, content.getText('\n'))


def save_to_file(content, filename):
    path = os.path.dirname(filename)
    if not os.path.exists(path):
        os.makedirs(path)
    with open(filename, 'w', encoding='utf-8') as f:
        print('Write {} successful.'.format(filename))
        f.write(content)


def custom_strip(x):
    remove_tag1 = re.compile('</div>&#13;')
    remove_tag2 = re.compile('<div id="content">')
    x = re.sub(remove_tag1, '', x)
    x = re.sub(remove_tag2, '', x)
    return x.strip()


def download():
    real_file = os.path.abspath(os.path.join(TMP_PATH, CHAPTER_TMP))
    with open(real_file, 'r', encoding='utf-8') as infile:
        for href in infile:
            html = get_content(href.strip())
            target_file = os.path.join(DIR_PATH,
                                       get_filename(href.strip()) + '.txt')
            filename = os.path.abspath(target_file)
            save_to_file(custom_strip(html), filename)


def merge_file(filename):
    target_list = os.path.abspath(os.path.join(DIR_PATH))
    file_list = os.listdir(target_list)
    with open(filename, 'w', encoding='utf-8') as outfile:
        for f_name in file_list:
            real_file = os.path.abspath(os.path.join(DIR_PATH, f_name))
            print('Open {} successful.'.format(real_file))
            with open(real_file, 'r', encoding='utf-8') as infile:
                for line in infile:
                    pattern = re.compile(r'第(\w+)章[\ \　].*')
                    items = re.findall(pattern, line)
                    if items:
                        old_str = items[0]
                        new_str = '%d' % chinese2digits(old_str)
                        outfile.write(line.replace(old_str, new_str))
                    else:
                        outfile.write(line)
                outfile.write('\n\n')


def convert(filename):
    with open(filename + "1", 'w', encoding='utf-8') as outfile:
        with open(filename, 'r', encoding='utf-8') as infile:
            for line in infile:
                pattern = re.compile(r'第(\w+)章[\ \　].*')
                items = re.findall(pattern, line)
                if items:
                    print(items[0])
                    old_str = items[0]
                    if old_str.isdigit():
                        new_str = old_str
                    else:
                        new_str = '%d' % chinese2digits(old_str)
                        print('Convert Chinese {} successful.'.format(new_str))
                    outfile.write(line.replace(old_str, new_str))
                else:
                    outfile.write(line)
            outfile.write('\n\n')


if __name__ == "__main__":
    #get_chapter(config['url'], host=config['host'])
    #download()
    #merge_file('ok.txt')
    convert('ok.txt')

