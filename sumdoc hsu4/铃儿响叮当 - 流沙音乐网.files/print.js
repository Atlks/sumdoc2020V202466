<!--
function doPrint()
{
	var adBegin="<!--DVNEWS_AD_BEGIN-->";
	var adEnd="<!--DVNEWS_AD_END-->";
	var body;
	var css;
	var str="<html>\r\n";
	str += "<head>\r\n";
	str += "<meta http-equiv=\"content-type\" content=\"text/html; charset="+document.charset+"\">\r\n";
	str += "<title>"+document.title+"</title>\r\n";
	str += "<link rel=\"stylesheet\" href=\"print.css\" type=\"text/css\"/>\r\n";
	str += "<script type=\"text/javascript\" src=\"inc/imgResize.js\"></script>\r\n";
	str += "<body bgcolor=\"#ffffff\" topmargin=\"30\" leftmargin=\"5\" marginheight=\"50\" marginwidth=\"5\" onLoad=\"window.print();\" contentEditable=\"true\">\r\n";
	str += document.all.printScript.innerHTML;
	str += "\n<script type=\"text/javascript\">\r\nfunction doPrint(){window.print();}\r\n</script>\r\n";
//	document.all.printHide.style.display='none';
	body= document.all.printBody.innerHTML;
	//È¥µô¹ã¸æ
	if (body.indexOf(adBegin)>=0)
	{
		str+=body.substr(0,body.indexOf(adBegin));
		str+=body.substr(body.indexOf(adEnd)+adEnd.length,body.length);
	}else{
		str+=body;
	}
	str += "\r\n</body>\r\n</html>";
	document.write(str);
	document.close();
}
-->