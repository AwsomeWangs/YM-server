document.writeln("");
document.writeln("<style>");
document.writeln(".mask {");
document.writeln("	position: fixed;");
document.writeln("	bottom: 0;");
document.writeln("	left:100%;");
document.writeln("	width: 100%;");
document.writeln("	height: 125px;");
document.writeln("	background: url(\'http://127.0.0.1:8003/img/main6_bg.jpg\')  no-repeat scroll center;");
document.writeln("	z-index: 1001;");
document.writeln("}");
document.writeln(".closeee{");
document.writeln("	background: url(\'http://127.0.0.1:8003/img/closesanjiao.png\')  no-repeat center center;");
document.writeln("}");
document.writeln(".mask .gotoCenter {");
document.writeln("	display: block;");
document.writeln("	width: 1000px;");
document.writeln("	height: 140px;");
document.writeln("	margin: 0 auto;");
document.writeln("}");
document.writeln(".mask .closeee {");
document.writeln("	position: absolute;");
document.writeln("	top: 0;");
document.writeln("	left: 20px;");
document.writeln("	margin-left: 0px;");
document.writeln("	width: 40px;");
document.writeln("	height: 100%;");
document.writeln("	cursor: pointer;");
document.writeln("}");
document.writeln(".clickarea{");
document.writeln("	position: absolute;");
document.writeln("	top: 0;");
document.writeln("	left: 200px;");
document.writeln("	margin-left: 0px;");
document.writeln("	width: 100%;");
document.writeln("	height: 100%;");
document.writeln("	cursor: pointer;");
document.writeln("}");
document.writeln(".thumbnail {");
document.writeln("	position: fixed;");
document.writeln("	right: 0px;");
document.writeln("	bottom: 0px;");
document.writeln("	width: 184px;");
document.writeln("	height: 124px;");
document.writeln("	cursor: pointer;");
document.writeln("	/*display: none;*/");
document.writeln("}");
document.writeln("@-webkit-keyframes rotate {");
document.writeln("	from {-webkit-transform:rotate(0deg);}");
document.writeln("	to {-webkit-transform:rotate(360deg);}");
document.writeln("}");
document.writeln("</style>");
document.writeln("");
document.writeln("<!-- æ´»åŠ¨è§’æ ‡ -->");
document.writeln("<div class=\'mask\'>");
document.writeln("	<span class=\'closeee\'></span>");
document.writeln("	<a onclick=\'openZoosUrl();\' onclick=\'openZoosUrl();\'><span class=\'clickarea\'></span></a>");
document.writeln("	<div class=\'thumbnail\'>");
document.writeln("			<img src=\'http://127.0.0.1:8003/img/51.gif\'>");
document.writeln("	</div>");
document.writeln("</div>");


setTimeout(function () {
    $('.thumbnail').trigger('myClick')
}, 1000)
$('.thumbnail').on("myClick", function () {
    $(this).animate({
        "right": -146
    }, 400, function () {
        $(this).hide();
        $('.mask').animate({
            "left": 0
        }, 600);
    });
});
// åº•éƒ¨å¹¿å‘Šæ çš„æ”¶èµ·ä¸Žå±•å¼€
$('.thumbnail').on("click", function () {
    $(this).animate({
        "right": -146
    }, 400, function () {
        $(this).hide();
        $('.mask').animate({
            "left": 0
        }, 600);
    });
    openZoosUrl();
});
$('.closeee').on("click", function () {
    //var thumbWidth = $('.thumbnail').width();
    $('.mask').animate({
        "left": "100%"
    }, 600, function () {
        $('.thumbnail').show().animate({
            "right": 0
        }, 400);
    });
});
