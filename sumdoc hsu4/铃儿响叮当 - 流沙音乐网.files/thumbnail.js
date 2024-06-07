function WriteThumb(img,url,alt,blank,highlight)
{
	if (blank==null)
		blank=false;
	if (highlight==null)
		highlight=false;
	with(document){
		write("<table class='thumbnail'>");
		write("<tr><td>");
		write("<a href='");
		write(url);
		if (blank)
			write("' target='_blank'>");
		else
			write("'>");
		write("<img src='");
		write(img);
		write("' alt='");
		write(alt);
		write("' ");
		if (highlight){
			if (thumbHWidth>0){
				write(" width='");
				write(thumbHWidth);
				write("' ");
			}
			if (thumbHHeight>0){
				write(" height='");
				write(thumbHHeight);
				write("' ");
			}
		}else{
			if (thumbWidth>0){
				write(" width='");
				write(thumbWidth);
				write("' ");
			}
			if (thumbHeight>0){
				write(" height='");
				write(thumbHeight);
				write("' ");
			}
		}
		write("></a></td></tr></table>");
	}
}
