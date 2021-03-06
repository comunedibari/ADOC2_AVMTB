<?xml version="1.0" encoding="UTF-8"?>
<!--

  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
  
  Copyright 2008 by Sun Microsystems, Inc.
 
  OpenOffice.org - a multi-platform office productivity suite
 
  $RCSfile: header.xsl,v $
 
  $Revision: 1.3 $
 
  This file is part of OpenOffice.org.
 
  OpenOffice.org is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License version 3
  only, as published by the Free Software Foundation.
 
  OpenOffice.org is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License version 3 for more details
  (a copy is included in the LICENSE file that accompanied this code).
 
  You should have received a copy of the GNU Lesser General Public License
  version 3 along with OpenOffice.org.  If not, see
  <http://www.openoffice.org/license.html>
  for a copy of the LGPLv3 License.
 
-->
<!--
	For further documentation and updates visit http://xml.openoffice.org/odf2xhtml
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
	xmlns:config="urn:oasis:names:tc:opendocument:xmlns:config:1.0"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:dom="http://www.w3.org/2001/xml-events"
	xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0"
	xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
	xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
	xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
	xmlns:math="http://www.w3.org/1998/Math/MathML"
	xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
	xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
	xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
	xmlns:ooo="http://openoffice.org/2004/office"
	xmlns:oooc="http://openoffice.org/2004/calc"
	xmlns:ooow="http://openoffice.org/2004/writer"
	xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
	xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
	xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
	xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
	xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
	xmlns:xforms="http://www.w3.org/2002/xforms"
	xmlns:xlink="http://www.w3.org/1999/xlink"
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="chart config dc dom dr3d draw fo form math meta number office ooo oooc ooow script style svg table text xforms xlink xsd xsi xforms xsd xsi"
	xmlns="http://www.w3.org/1999/xhtml">


	<!-- ************** -->
	<!-- *** header *** -->
	<!-- ************** -->

	<xsl:template name="create-header">
		<xsl:param name="globalData" />

		<xsl:element name="head">
		
			<xsl:element name="meta">
				<xsl:attribute name="http-equiv">Content-Type</xsl:attribute>
				<xsl:attribute name="content">text/html; charset=utf-8</xsl:attribute>
			</xsl:element>

			<xsl:if test="$debugEnabled"><xsl:message>CSS variable ready, header will be created....</xsl:message></xsl:if>
			<!-- constructing the css header simulating inheritance of style-families by style order -->
			<xsl:call-template name='create-css-styleheader'>
				<xsl:with-param name="globalData" select="$globalData" />
			</xsl:call-template>
			<xsl:if test="$debugEnabled"><xsl:message>CSS header creation finished!</xsl:message></xsl:if>
		</xsl:element>

	</xsl:template>


	<!-- Creating a CSS style header from the collected styles of the 'globalData' parameter -->
	<xsl:template name='create-css-styleheader'>
		<xsl:param name="globalData" />

		<xsl:element name="style">
			<xsl:attribute name="type">text/css</xsl:attribute>
<xsl:text>
	</xsl:text>
	<xsl:call-template name='create-page-layout'>
		<xsl:with-param name="globalData" select="$globalData" />
	</xsl:call-template>
<xsl:text>table { border-collapse:collapse; border-spacing:0; empty-cells:show }
	</xsl:text>
	<xsl:choose>
		<xsl:when test="/*/office:body/office:spreadsheet"><xsl:text>td, th { vertical-align:top; font-size:10pt;}
	</xsl:text></xsl:when>
		<xsl:otherwise><xsl:text>td, th { vertical-align:top; font-size:12pt;}
	</xsl:text></xsl:otherwise>
	</xsl:choose>
<xsl:text>h1, h2, h3, h4, h5, h6 { clear:both }
	</xsl:text>
<xsl:text>ol, ul { margin:0; padding:0;}
	</xsl:text>
<xsl:text>li { list-style: none; margin:0; padding:0;}
	</xsl:text>
<xsl:text>li span.odfLiEnd { clear: both; line-height:0; width:0; height:0; margin:0; padding:0; }
	</xsl:text>
<xsl:text>span.footnodeNumber { padding-right:1em; }
	</xsl:text>
<xsl:text>* { margin:0; }
	</xsl:text>
			<xsl:call-template name="write-mapped-CSS-styles">
				<xsl:with-param name="globalData" select="$globalData" />
			</xsl:call-template>
		</xsl:element>
	</xsl:template>

	<xsl:template name="write-mapped-CSS-styles">
		<xsl:param name="globalData" />
		<xsl:param name="styleNo" select="1"/>
		<xsl:param name="emptyStyles"/>

		<xsl:choose>
			<xsl:when test="$globalData/all-styles/style[$styleNo]">
			<!-- If there is still a style to be written -->
				<!-- setting the context -->
				<xsl:for-each select="$globalData/all-styles/style[$styleNo]">
				<xsl:choose>
					<xsl:when test="final-properties != ''">
					<!-- NOTE: easy process, as only the style family in conjunction with the style name, makes the style unambigous -->
				<xsl:text>.</xsl:text><!--<xsl:value-of select="@style:family" /><xsl:text>:</xsl:text>--><xsl:value-of select="translate(@style:name, '.,;: %()[]/\+', '_____________')"/><xsl:text> { </xsl:text> <xsl:value-of select="final-properties" /><xsl:text>}
	</xsl:text>
						<xsl:call-template name="write-mapped-CSS-styles">
							<xsl:with-param name="globalData" select="$globalData" />
							<xsl:with-param name="emptyStyles" select="$emptyStyles"/>
							<xsl:with-param name="styleNo" select="$styleNo + 1"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="write-mapped-CSS-styles">
							<xsl:with-param name="globalData" select="$globalData" />
							<xsl:with-param name="emptyStyles" select="concat($emptyStyles, '.', @style:name, ' ')"/>
							<xsl:with-param name="styleNo" select="$styleNo + 1"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
			<!-- Otherwise all styles have been processed and the empty styles have to be given out -->
				<xsl:comment> ODF styles with no properties representable as CSS </xsl:comment><xsl:text>
	</xsl:text><xsl:value-of select="$emptyStyles"/><xsl:text>{ }
	</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	<!-- Creating CSS page layout based on first office master style -->
	<xsl:template name='create-page-layout'>
		<xsl:param name="globalData" />

<xsl:text>@page { </xsl:text>

		<xsl:call-template name="page-size">
			<xsl:with-param name="globalData"   select="$globalData" />
		</xsl:call-template>
		<xsl:call-template name="page-margin">
			<xsl:with-param name="globalData"   select="$globalData" />
		</xsl:call-template>

<xsl:text> }
	</xsl:text>

	</xsl:template>


	<xsl:template name="page-size">
		<xsl:param name="globalData" />

		<!-- approximation as attribute belongs to a page style, which won't work in XHTML -->
		<xsl:variable name="pageProperties" select="$globalData/styles-file/*/office:automatic-styles/style:page-layout[1]/style:page-layout-properties"/>

		<xsl:variable name="printOrientation"  select="$pageProperties/@style:print-orientation" />
		<xsl:variable name="pageWidth"         select="$pageProperties/@fo:page-width" />
		<xsl:variable name="pageHeight"        select="$pageProperties/@fo:page-height" />
		<xsl:choose>
			<xsl:when test="$pageWidth and $pageHeight">
				<xsl:text>size: </xsl:text>
				<xsl:value-of select="$pageWidth" />
				<xsl:text> </xsl:text>
				<xsl:value-of select="$pageHeight" />
				<xsl:text>; </xsl:text>
			</xsl:when>
			<xsl:when test="$printOrientation">
				<xsl:text>size: </xsl:text>
				<xsl:value-of select="$printOrientation" />
				<xsl:text>; </xsl:text>
			</xsl:when>
		</xsl:choose>
	</xsl:template>



	<xsl:template name="page-margin">
		<xsl:param name="globalData" />

		<!-- approximation as attribute belongs to a page style, which won't work in XHTML -->
		<xsl:variable name="pageProperties" select="$globalData/styles-file/*/office:automatic-styles/style:page-layout[1]/style:page-layout-properties"/>

		<xsl:variable name="marginTop"  select="$pageProperties/@fo:margin-top" />
		<xsl:if test="$marginTop">
			<xsl:text>margin-top: </xsl:text>
			<xsl:value-of select="$marginTop" />
			<xsl:text>; </xsl:text>
		</xsl:if>
		<xsl:variable name="marginBottom"  select="$pageProperties/@fo:margin-bottom" />
		<xsl:if test="$marginBottom">
			<xsl:text>margin-bottom: </xsl:text>
			<xsl:value-of select="$marginBottom" />
			<xsl:text>; </xsl:text>
		</xsl:if>
		<xsl:variable name="marginLeft"  select="$pageProperties/@fo:margin-left" />
		<xsl:if test="$marginLeft">
			<xsl:text>margin-left: </xsl:text>
			<xsl:value-of select="$marginLeft" />
			<xsl:text>; </xsl:text>
		</xsl:if>
		<xsl:variable name="marginRight"  select="$pageProperties/@fo:margin-right" />
		<xsl:if test="$marginRight">
			<xsl:text>margin-right: </xsl:text>
			<xsl:value-of select="$marginRight" />
		</xsl:if>
	</xsl:template>


	<!-- *************************** -->
	<!-- *** Common XHTML header *** -->
	<!-- *************************** -->

	<xsl:template name='xhtml-header-properties'>
		<xsl:param name="globalData" />

		<xsl:variable name="netloc">
		<xsl:for-each select="$globalData/meta-file/*/office:meta/meta:user-defined">
		<xsl:if test="./@meta:name='ODF.base'">
		<xsl:value-of select="." />
		</xsl:if>
		</xsl:for-each>
		<xsl:for-each select="$globalData/meta-file/*/office:meta/meta:user-defined">
		<xsl:if test="./@meta:name='ODF.filename'">
		<xsl:value-of select="." />
		</xsl:if>
		</xsl:for-each>
		</xsl:variable>

		

		<!-- explicit output content-type for low-tech browser (e.g. IE6) -->
		<xsl:element name="meta">
			<xsl:attribute name="http-equiv">Content-Type</xsl:attribute>
			<xsl:attribute name="content">text/html; charset=utf-8</xsl:attribute>
		</xsl:element>

		<!-- title of document for browser frame title -->







		<!-- W3C GRDDL Profile -->
		<!--
		<link rel="transformation" href="http://xml.openoffice.org/odf2xhtml/rdf-extract.xsl" />
		-->

		<!-- base URL of document for resolving relative links  -->
		<xsl:element name="base">
			<xsl:attribute name="href">
				<!-- earlier 'targetURL' was used for an absoulte reference of base provided by the Office (file URL)
					<xsl:value-of select="$targetURL" />
					now '.' let relative links work, even if document has been moved -->
				<xsl:text>.</xsl:text>
			</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<!-- generic template for adding common meta tags -->
	<xsl:template name="add-meta-tag">
		<xsl:param name="meta-name" />
		<xsl:param name="meta-data" />
		<xsl:param name="meta-enc" />
		<xsl:param name="meta-lang" />

		<xsl:if test="$meta-data">
			<xsl:element name="meta">
				<xsl:attribute name="name">
					<xsl:value-of select="$meta-name" />
				</xsl:attribute>
				<xsl:attribute name="content">
					<xsl:value-of select="$meta-data" />
				</xsl:attribute>
				<xsl:if test="$meta-enc">
				<xsl:attribute name="scheme">
					<xsl:value-of select="$meta-enc" />
				</xsl:attribute>
				</xsl:if>
				<xsl:if test="$meta-lang">
				<xsl:attribute name="lang" namespace="http://www.w3.org/XML/1998/namespace">
					<xsl:value-of select="$meta-lang" />
				</xsl:attribute>
				</xsl:if>
			</xsl:element>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
