﻿/*
 Copyright (c) CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.html or http://ckeditor.com/license
*/
CKEDITOR.plugins.add("wordcount",{lang:"ar,bg,ca,cs,da,de,el,en,es,eu,fa,fi,fr,he,hr,hu,it,ko,ja,nl,no,pl,pt,pt-br,ru,sk,sv,tr,uk,zh-cn,zh,ro",version:"1.17.6",requires:"htmlwriter,notification,undo",bbcodePluginLoaded:!1,onLoad:function(){CKEDITOR.document.appendStyleSheet(this.path+"css/wordcount.css")},init:function(e){function k(d){return"cke_wordcount_"+d.name}function v(d){if(bbcodePluginLoaded)return d.replace(/\[.*?\]/gi,"");var a=document.createElement("div");d=t(d);a.innerHTML=d;return""==
a.textContent&&"undefined"==typeof a.innerText?"":a.textContent||a.innerText}function t(d){if(a.filter instanceof CKEDITOR.htmlParser.filter){d=CKEDITOR.htmlParser.fragment.fromHtml(d);var c=new CKEDITOR.htmlParser.basicWriter;a.filter.applyTo(d);d.writeHtml(c);return c.getHtml()}return d}function w(d){if(a.countHTML)return a.countBytesAsChars?x(t(d)):t(d).length;var c;if(e.config.fullPage&&(c=d.search(/<body>/i),-1!=c)){var b=d.search(/<\/body>/i);d=d.substring(c+6,b)}c=d;a.countSpacesAsChars||(c=
d.replace(/\s/g,"").replace(/&nbsp;/g,""));c=a.countLineBreaks?c.replace(/(\r\n|\n|\r)/gm," "):c.replace(/(\r\n|\n|\r)/gm,"").replace(/&nbsp;/gi," ");c=v(c).replace(/^([\t\r\n]*)$/,"");return a.countBytesAsChars?x(c):c.length}function x(a){var c=0,e=a.length,b;a=String(a||"");for(b=0;b<e;b++)var f=encodeURI(a[b]).split("%").length,c=c+(1==f?1:f-1);return c}function y(a){return a.replace(/&nbsp;/g," ").replace(/(<([^>]+)>)/ig,"").replace(/^\s*$[\n\r]{1,}/gm,"++").split("++").length}function z(a){a=
a.replace(/(\r\n|\n|\r)/gm," ").replace(/^\s+|\s+$/g,"").replace("\x26nbsp;"," ");a=v(a);a=a.split(/\s+/);for(var c=a.length-1;0<=c;c--)a[c].match(/^([\s\t\r\n]*)$/)&&a.splice(c,1);return a.length}function l(d){if(document.getElementById(k(d))){var c=0,b=0,g=0,f;d.fire("beforeGetData",{firedBy:"wordCount.updateCounter"},e);f=d.getData(!0);d.fire("getData",{dataValue:f,firedBy:"wordCount.updateCounter"},e);f&&(a.showCharCount&&(g=w(f)),a.showParagraphs&&(c=y(f)),a.showWordCount&&(b=z(f)));f=A;a.showRemaining?
(f=0<=a.maxCharCount?f.replace("%charCount%",a.maxCharCount-g):f.replace("%charCount%",g),f=0<=a.maxWordCount?f.replace("%wordCount%",a.maxWordCount-b):f.replace("%wordCount%",b),f=0<=a.maxParagraphs?f.replace("%paragraphsCount%",a.maxParagraphs-c):f.replace("%paragraphsCount%",c)):f=f.replace("%wordCount%",b).replace("%charCount%",g).replace("%paragraphsCount%",c);(d.config.wordcount||(d.config.wordcount={})).wordCount=b;(d.config.wordcount||(d.config.wordcount={})).charCount=g;CKEDITOR.env.gecko?
document.getElementById(k(d)).innerHTML=f:document.getElementById(k(d)).innerText=f;if(g==m&&b==n&&c==p)return(g==a.maxCharCount||b==a.maxWordCount||c>a.maxParagraphs)&&d.fire("saveSnapshot"),!0;f=b-n;var h=g-m,l=c-p;n=b;m=g;p=c;-1==n&&(n=b);-1==m&&(m=g);-1==p&&(p=c);-1<a.maxWordCount&&b>a.maxWordCount&&0<f||-1<a.maxCharCount&&g>a.maxCharCount&&0<h||-1<a.maxParagraphs&&c>a.maxParagraphs&&0<l?(f=u,u=!0,a.warnOnLimitOnly||a.hardLimit&&d.execCommand("undo"),f||(document.getElementById(k(d)).className=
"cke_path_item cke_wordcountLimitReached",d.fire("limitReached",{firedBy:"wordCount.limitReached"},e))):(-1==a.maxWordCount||b<=a.maxWordCount)&&(-1==a.maxCharCount||g<=a.maxCharCount)&&(-1==a.maxParagraphs||c<=a.maxParagraphs)?(u=!1,a.warnOnLimitOnly||d.fire("saveSnapshot"),document.getElementById(k(d)).className="cke_path_item"):d.fire("saveSnapshot");d.wordCount={paragraphs:c,wordCount:b,charCount:g};a.charCountGreaterThanMaxLengthEvent&&a.charCountLessThanMaxLengthEvent&&(g>a.maxCharCount&&-1<
a.maxCharCount?a.charCountGreaterThanMaxLengthEvent(g,a.maxCharCount):a.charCountLessThanMaxLengthEvent(g,a.maxCharCount));a.wordCountGreaterThanMaxLengthEvent&&a.wordCountLessThanMaxLengthEvent&&(b>a.maxWordCount&&-1<a.maxWordCount?a.wordCountGreaterThanMaxLengthEvent(b,a.maxWordCount):a.wordCountLessThanMaxLengthEvent(b,a.maxWordCount));return!0}}var b="",n=-1,m=-1,p=-1,u=!1,q=0,h=null,r=function(a,c,b){if("undefined"!=typeof document.dispatchEvent){a="ckeditor.wordcount."+a;var e;c={bubbles:!1,
cancelable:!0,detail:{currentLength:c,maxLength:b}};try{e=new CustomEvent(a,c)}catch(f){e=document.createEvent("CustomEvent"),e.initCustomEvent(a,c.bubbles,c.cancelable,c.detail)}document.dispatchEvent(e)}},a=CKEDITOR.tools.extend({showRemaining:!1,showParagraphs:!0,showWordCount:!0,showCharCount:!1,countBytesAsChars:!1,countSpacesAsChars:!1,countHTML:!1,countLineBreaks:!1,hardLimit:!0,warnOnLimitOnly:!1,maxWordCount:-1,maxCharCount:-1,maxParagraphs:-1,filter:null,pasteWarningDuration:0,wordCountGreaterThanMaxLengthEvent:function(a,
c){r("wordCountGreaterThanMaxLengthEvent",a,c)},charCountGreaterThanMaxLengthEvent:function(a,c){r("charCountGreaterThanMaxLengthEvent",a,c)},wordCountLessThanMaxLengthEvent:function(a,c){r("wordCountLessThanMaxLengthEvent",a,c)},charCountLessThanMaxLengthEvent:function(a,c){r("charCountLessThanMaxLengthEvent",a,c)}},e.config.wordcount||{},!0);a.showParagraphs&&(-1<a.maxParagraphs?a.showRemaining?b+="%paragraphsCount% "+e.lang.wordcount.ParagraphsRemaining:(b+=e.lang.wordcount.Paragraphs+" %paragraphsCount%",
b+="/"+a.maxParagraphs):b+=e.lang.wordcount.Paragraphs+" %paragraphsCount%");a.showParagraphs&&(a.showWordCount||a.showCharCount)&&(b+=", ");a.showWordCount&&(-1<a.maxWordCount?a.showRemaining?b+="%wordCount% "+e.lang.wordcount.WordCountRemaining:(b+=e.lang.wordcount.WordCount+" %wordCount%",b+="/"+a.maxWordCount):b+=e.lang.wordcount.WordCount+" %wordCount%");a.showCharCount&&a.showWordCount&&(b+=", ");a.showCharCount&&(-1<a.maxCharCount?a.showRemaining?b+="%charCount% "+e.lang.wordcount[a.countHTML?
"CharCountWithHTMLRemaining":"CharCountRemaining"]:(b+=e.lang.wordcount[a.countHTML?"CharCountWithHTML":"CharCount"]+" %charCount%",b+="/"+a.maxCharCount):b+=e.lang.wordcount[a.countHTML?"CharCountWithHTML":"CharCount"]+" %charCount%");var A=b;bbcodePluginLoaded="undefined"!=typeof e.plugins.bbcode;e.on("key",function(a){"source"===e.mode&&(clearTimeout(q),q=setTimeout(l.bind(this,a.editor),250))},e,null,100);e.on("change",function(d){var c=-1<a.maxWordCount&&5>a.maxWordCount-n||-1<a.maxCharCount&&
20>a.maxCharCount-m||-1<a.maxParagraphs&&1>a.maxParagraphs-p?5:250;clearTimeout(q);q=setTimeout(l.bind(this,d.editor),c)},e,null,100);e.on("uiSpace",function(a){e.elementMode===CKEDITOR.ELEMENT_MODE_INLINE?"top"==a.data.space&&(a.data.html+='\x3cdiv class\x3d"cke_wordcount" style\x3d"" title\x3d"'+e.lang.wordcount.title+'"\x3e\x3cspan id\x3d"'+k(a.editor)+'" class\x3d"cke_path_item"\x3e\x26nbsp;\x3c/span\x3e\x3c/div\x3e'):"bottom"==a.data.space&&(a.data.html+='\x3cdiv class\x3d"cke_wordcount" style\x3d"" title\x3d"'+
e.lang.wordcount.title+'"\x3e\x3cspan id\x3d"'+k(a.editor)+'" class\x3d"cke_path_item"\x3e\x26nbsp;\x3c/span\x3e\x3c/div\x3e')},e,null,100);e.on("dataReady",function(a){l(a.editor)},e,null,100);e.on("paste",function(d){if(!a.warnOnLimitOnly&&(0<a.maxWordCount||0<a.maxCharCount||0<a.maxParagraphs)){var c=-1,b=-1,e=-1;d.editor.fire("beforeGetData",{firedBy:"wordCount.onPaste"},d.editor);var f=d.editor.getData(!0);d.editor.fire("getData",{dataValue:f,firedBy:"wordCount.onPaste"},d.editor);f+=d.data.dataValue;
a.showCharCount&&(b=w(f));a.showWordCount&&(c=z(f));a.showParagraphs&&(e=y(f));null===h&&(h=new CKEDITOR.plugins.notification(d.editor,{message:d.editor.lang.wordcount.pasteWarning,type:"warning",duration:a.pasteWarningDuration}));0<a.maxCharCount&&b>a.maxCharCount&&a.hardLimit&&(h.isVisible()||h.show(),d.cancel());0<a.maxWordCount&&c>a.maxWordCount&&a.hardLimit&&(h.isVisible()||h.show(),d.cancel());0<a.maxParagraphs&&e>a.maxParagraphs&&a.hardLimit&&(h.isVisible()||h.show(),d.cancel())}},e,null,100);
e.on("afterPaste",function(a){l(a.editor)},e,null,100)}});