<?xml version="1.0" encoding="UTF-8"?>
<!--
  ~ The MIT License (MIT)
  ~
  ~ Copyright (c) 2014 Parasoft Corporation
  ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a copy
  ~ of this xsl file and associated documentation files (the "XSL"), to deal
  ~ in the XSL without restriction, including without limitation the rights
  ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  ~ copies of the XSL, and to permit persons to whom the XSL is
  ~ furnished to do so, subject to the following conditions:
  ~
  ~ The above copyright notice and this permission notice shall be included in
  ~ all copies or substantial portions of the XSL
  ~
  ~ THE XSL IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  ~ OUT OF OR IN CONNECTION WITH THE XSL OR THE USE OR OTHER DEALINGS IN
  ~ THE XSL.
 -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" encoding="UTF-8" indent="yes" />


	<xsl:template match="/">
	<xsl:choose>
		<xsl:when test="ResultsSession/@toolName='SOAtest'">
            <testsuite name="{ExecutedTestsDetails/Total/Project/@name}" time="0"
                failures="{ExecutedTestsDetails/Total/@fail}">
			<xsl:apply-templates select="ResultsSession/FunctionalTests"></xsl:apply-templates>
			</testsuite>
		</xsl:when>
		<xsl:otherwise>
			<xsl:apply-templates select="ResultsSession/Exec"></xsl:apply-templates>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:template>
    <!-- Logic for parsing SOAtest reports -->
    <xsl:template match="FunctionalTests">
        
        <xsl:apply-templates select="FuncViols" />
        
    </xsl:template>
    
    <xsl:template match="FuncViols">
        <xsl:choose>
        <xsl:when test="FuncViol">
            <xsl:apply-templates select="FuncViol" />
        </xsl:when>
        <xsl:otherwise>
            <testcase classname="AllTests" name="All Tests Passed" time="0"/>
        </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="FuncViol">
        <testcase classname="{@locFile}" name="{@msg}" time="0">
        <failure type="{@taskType}" message="{@violationDetails}" />
        <system-err>
            <xsl:text>Traffic Viewer </xsl:text>
            <xsl:text>
            </xsl:text>
            <xsl:text>Request:</xsl:text>
            <xsl:text>
            </xsl:text>
            <xsl:value-of select="@requestTrafficBody" />
            <xsl:text>
            </xsl:text>   
            <xsl:text>Response:</xsl:text>
            <xsl:text>
            </xsl:text>
            <xsl:value-of select="@responseTrafficBody" />
        </system-err>
        </testcase>
    </xsl:template>
    <!-- End SOAtest report logic -->
    <!-- Report logic for Jtest, C++Test, and dotTest -->
	<xsl:template match="Exec">
		<testsuite name="{Summary/Projects/Project/@name}" time="0"
			tests="{Summary/Projects/Project/@testCases}" failures="{Summary/Projects/Project/@fail}">
			<xsl:apply-templates select="*" />
		</testsuite>
	</xsl:template>

	<xsl:template match="Goals">
		<properties>
			<xsl:apply-templates select="Goal" />
		</properties>
	</xsl:template>

	<xsl:template match="Goal">
		<property name="{@name}" value="{@type}" />
	</xsl:template>

	<xsl:template match="ExecViols">
	   <!-- Apply ExecViol if template exists, otherwise include empty testcase to avoid build failure for no testcases -->
        <xsl:choose>
          <xsl:when test="ExecViol">
		      <xsl:apply-templates select="ExecViol" />
		  </xsl:when>
		  <xsl:otherwise>
		      <testcase classname="AllTests" name="All Tests Passed" time="0"/>
		  </xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="ExecViol">
		<testcase classname="{@locFile}" name="{@testName}" time="0">
			<xsl:apply-templates select="Thr" />
		</testcase>
	</xsl:template>

	<xsl:template match="Thr">
		<xsl:apply-templates select="ThrPart" />
	</xsl:template>

	<xsl:template match="ThrPart">
		<failure type="{@clName}" message="{@prnMsg}" />
		<system-err>
			<xsl:text>Trace </xsl:text>
			<xsl:apply-templates select="Trace" />
		</system-err>
	</xsl:template>

	<xsl:template match="Trace">
		<xsl:text>
Line :</xsl:text>
		<xsl:value-of select="@ln" />
		<xsl:text>    File :</xsl:text>
		<xsl:value-of select="@fileName" />
		<xsl:text>    Method :</xsl:text>
		<xsl:value-of select="@metName" />
	</xsl:template>
	<!-- End logic for Jtest, C++Test, and dotTest -->
	
	<xsl:template match="text()" />

</xsl:stylesheet>