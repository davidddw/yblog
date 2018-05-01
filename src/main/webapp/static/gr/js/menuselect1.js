/**
 * Created by d05660ddw on 2017/3/15.
 */
var nav = document.getElementById("menu");
var links = nav.getElementsByTagName("li");
var lilen = nav.getElementsByTagName("a"); //鍒ゆ柇鍦板潃
var currenturl = document.location.href;
var last = 0;
for (var i=0;i<links.length;i++) {
    var linkurl = lilen[i].getAttribute("href");
    if(currenturl.indexOf(linkurl)!=-1) {
        last = i;
    }
}
links[last].className = "navon";