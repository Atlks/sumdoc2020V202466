<!--
//Pop-it menu- By Dynamic Drive - Modified by Wbird
//For full source code and more DHTML scripts, visit http://www.dynamicdrive.com
//This credit MUST stay intact for use
var menuOffX=0	//菜单距连接文字最左端距离
var menuOffY=18	//菜单距连接文字顶端距离

var fo_shadows=new Array()
var linkset=new Array()

////No need to edit beyond here

var ie4=document.all&&navigator.userAgent.indexOf("Opera")==-1
var ie55up = IsIE55Up();
var ns6=document.getElementById&&!document.all
var ns4=document.layers
var overIframe = null;
var jsdone = false;	// 未执行其它js前不出菜单

function showmenu(e,index,p,paging){
	if (!jsdone)
		return false;
	//p为当前页数,paging为当前是不是翻页
	if (!document.all&&!document.getElementById&&!document.layers)
		return
	
	which=linkset[index]
	var pSize=10	//每页连接数
	var pNum=Math.floor((which.length-1)/pSize)+1		//页数
	
	clearhidemenu()
	ie_clearshadow()
	
	//设置菜单内容
	if (pNum==1){
		which=which.join("")
	}else{
		which=which.slice( (p-1)*pSize, p*pSize )
		which=which.join("")
		which+="<div class=\"menuitems\" >"
		if (p==1)
		{
			which+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face=webdings color=gray>7</font>"
		}else{
			which+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face=webdings style=cursor:hand onclick=showmenu(event,"+ index +","+ (p-1) +",true) >7</font>"
		}
		if (p==pNum)
		{
			which+="<font face=webdings color=gray>8</font>"
		}else{
			which+="<font face=webdings style=cursor:hand onclick=showmenu(event,"+ index +","+ (p+1) +",true) >8</font>"
		}
		which+="</div>"
	}
	
	menuobj=ie4? document.all.popmenu : ns6? document.getElementById("popmenu") : ns4? document.popmenu : ""
	menuobj.thestyle=(ie4||ns6)? menuobj.style : menuobj
	
	if (ie4||ns6)
		menuobj.innerHTML=which
	else{
		menuobj.document.write("<layer name=\"gui\" bgColor=\"#E6E6E6\" width=\"165\" onmouseover=\"clearhidemenu()\" onmouseout=\"hidemenu()\">"+which+"</layer>")
		menuobj.document.close()
	}
	menuobj.contentwidth=(ie4||ns6)? menuobj.offsetWidth : menuobj.document.gui.document.width
	menuobj.contentheight=(ie4||ns6)? menuobj.offsetHeight : menuobj.document.gui.document.height
	
	eventX=ie4? event.clientX : ns6? e.clientX : e.x
	eventY=ie4? event.clientY : ns6? e.clientY : e.y
	
	var rightedge=ie4? document.body.clientWidth-eventX : window.innerWidth-eventX
	var bottomedge=ie4? document.body.clientHeight-eventY : window.innerHeight-eventY
		
	if (!paging)
	{	
		if (rightedge<menuobj.contentwidth)
			menuobj.thestyle.left=ie4? document.body.scrollLeft+eventX-menuobj.contentwidth+menuOffX : ns6? window.pageXOffset+eventX-menuobj.contentwidth : eventX-menuobj.contentwidth
		else
			menuobj.thestyle.left=ie4? ie_x(event.srcElement)+menuOffX : ns6? window.pageXOffset+eventX : eventX
		
		if (bottomedge<menuobj.contentheight)
			menuobj.thestyle.top=ie4? document.body.scrollTop+eventY-menuobj.contentheight-event.offsetY+menuOffY : ns6? window.pageYOffset+eventY-menuobj.contentheight : eventY-menuobj.contentheight
		else
			menuobj.thestyle.top=ie4? ie_y(event.srcElement)+menuOffY : ns6? window.pageYOffset+eventY : eventY
	}
		
	menuobj.thestyle.visibility="visible"
	ie_dropshadow(menuobj,"#999999",3)
	DivOverSel(menuobj);
	return false
}

function ie_y(e){  
	var t=e.offsetTop;  
	while(e=e.offsetParent){  
		t+=e.offsetTop;  
	}  
	return t;  
}  
function ie_x(e){  
	var l=e.offsetLeft;  
	while(e=e.offsetParent){  
		l+=e.offsetLeft;  
	}  
	return l;  
}  
function ie_dropshadow(el, color, size)
{
	var i;
	for (i=size; i>0; i--)
	{
		var rect = document.createElement('div');
		var rs = rect.style
		rs.position = 'absolute';
		rs.left = (el.style.posLeft + i) + 'px';
		rs.top = (el.style.posTop + i) + 'px';
		rs.width = el.offsetWidth + 'px';
		rs.height = el.offsetHeight + 'px';
		rs.zIndex = el.style.zIndex - i;
		rs.backgroundColor = color;
		var opacity = 1 - i / (i + 1);
		rs.filter = 'alpha(opacity=' + (100 * opacity) + ')';
		el.insertAdjacentElement('afterEnd', rect);
		fo_shadows[fo_shadows.length] = rect;
	}
}
function ie_clearshadow()
{
	for(var i=0;i<fo_shadows.length;i++)
	{
		if (fo_shadows[i])
			fo_shadows[i].style.display="none"
	}
	fo_shadows=new Array();
}


function contains_ns6(a, b) {
	while (b.parentNode)
		if ((b = b.parentNode) == a)
			return true;
	return false;
}

function hidemenu(){
	if (window.menuobj)
		window.menuobj.thestyle.visibility=(ie4||ns6)? "hidden" : "hide"
	ie_clearshadow()
	DivOutSel();
}

function dynamichide(e){
	if (ie4&&!menuobj.contains(e.toElement))
		hidemenu()
	else if (ns6&&e.currentTarget!= e.relatedTarget&& !contains_ns6(e.currentTarget, e.relatedTarget))
		hidemenu()
}

function delayhidemenu(){
	if (ie4||ns6||ns4)
		delayhide=setTimeout("hidemenu()",500)
}

function clearhidemenu(){
	if (window.delayhide)
		clearTimeout(delayhide)
}

function highlightmenu(e,state){
	if (document.all)
		source_el=event.srcElement
	else if (document.getElementById)
		source_el=e.target
	if (source_el.className=="menuitems"){
		source_el.id=(state=="on")? "mouseoverstyle" : ""
	}
	else{
		while(source_el.id!="popmenu"){
			source_el=document.getElementById? source_el.parentNode : source_el.parentElement
			if (source_el.className=="menuitems"){
				source_el.id=(state=="on")? "mouseoverstyle" : ""
			}
		}
	}
}

function DivOverSel(obj)
{
	// 只有IE5.5以上Iframe的z-index才有效
	if (ie55up)
	{
		if (overIframe == null)
		{
			overIframe = document.createElement("<iframe src='about:blank' style='position:absolute;left:0;top:0;z-index:998;display:none' scrolling='no' frameborder='0'></iframe>");
		}
		document.body.insertAdjacentElement("beforeEnd",overIframe);
		with(overIframe.style)
		{
			top = obj.style.top;
			left = obj.style.left;
			width = obj.offsetWidth;
			height = obj.offsetHeight;
			overIframe.style.visibility='visible';
			display = 'block';
		}
		obj.style.zIndex = "999";
	}
}

function DivOutSel()
{
	if (ie55up)
	{
		if (overIframe != null)
		{
			overIframe.style.visibility='hidden';
		}
	}
}

// 是否IE5.5以上版本
function IsIE55Up () {
	var agt = navigator.userAgent.toLowerCase();
	var isIE = (agt.indexOf("msie")!=-1);
	if (isIE)
	{
		var stIEVer = agt.substring(agt.indexOf("msie ") + 5);
		var verIEFull = parseFloat(stIEVer);
		return verIEFull >= 5.5;
	}
	return false;
}

if (ie4||ns6)
document.onclick=hidemenu
-->