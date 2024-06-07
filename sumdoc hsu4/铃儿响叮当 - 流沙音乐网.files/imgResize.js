var imgMaxWidth=400;
function ImgLoad(obj)
{
	for(var i=0;i<obj.all.tags("img").length;i++){
		var o=obj.all.tags("img")[i];
		if (o.width>imgMaxWidth){
			o.width=imgMaxWidth;
			o.removeAttribute("height");
			o.style.cursor="hand";
			o.resized=1;
		}
	}
}
function ImgClick(obj)
{
	if (obj.resized==1 && obj.parentElement.tagName!="A")
		window.open(obj.src);
}
function bbimg(o){
	if (o.resized==1){
		//var zoom=parseInt(o.style.zoom, 10)||100;zoom+=event.wheelDelta/12;if (zoom>0) o.style.zoom=zoom+'%';
	}
	return false;
}
