<?xml version="1.0" encoding="gb2312"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
     <title>2002年北大青鸟APTech培训中心学生成绩单</title>
     <h2 align="center">学生成绩单</h2> 
         <table border="1" cellpadding="0" align="center">
     <tr align="center" bgcolor="#dadada"><td>编号</td><td>姓名</td><td>性别</td><td>生日</td><td>成绩</td></tr>
     <xsl:for-each select="roster/student">
     <xsl:sort select="@ID" data-type="number"/>
       <tr>
         <xsl:if test="@ID mod 2=0">
         		<xsl:attribute name="bgcolor">green</xsl:attribute>
         </xsl:if>		
       	 <td><xsl:value-of select="@ID"/></td>
         <td>
         		<font>
         		  <xsl:if test="sex='女'">
         		  <xsl:attribute name="color">blue</xsl:attribute>
	         		</xsl:if>
         			<xsl:value-of select="name"/>
       		</font>
         </td>
         <td><xsl:value-of select="sex"/></td>
         <td><xsl:value-of select="birthday"/></td>
         <td><xsl:value-of select="score"/></td>         	
       </tr>
     </xsl:for-each>
     <xsl:variable name="vsum">
     		<xsl:value-of select="sum(roster/student/score)"/>
     </xsl:variable>	
     <xsl:variable name="vcount">
     		<xsl:value-of select="count(roster/student)"/>
     </xsl:variable>		       
     <tr>
       <td colspan="4" align="center">总分</td>
       <td><xsl:value-of select="$vsum"/></td>
     </tr>
     <tr>  
       <td colspan="4" align="center">总人数</td>
       <td><xsl:value-of select="$vcount"/></td>
     </tr>
     <tr>
       <td colspan="4" align="center">平均分</td>
       <td><xsl:value-of select="round($vsum div $vcount*10) div 10"/></td>
     </tr>
</table>
<hr/>
 <div align="center">
 		60分以下共<B><xsl:value-of select="count(roster/student[score&lt;60])"/></B>人<br/>
 		85(包含)以上女生共<B><xsl:value-of select="count(roster/student[(score&gt;85 or score=85) and sex='女'])"/></B>人
 		ID在104以上的：<xsl:value-of select="count(roster/student[@ID&gt;104])"/>
 </div>		
 </xsl:template> 
</xsl:stylesheet>
