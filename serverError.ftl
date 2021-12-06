<html>
<meta charset="UTF-8">
<head>
<style>
body{
	font-family: 'Homenaje',sans-serif;
	font-size: 18;
	margin:10px;
	padding:10px;
	letter-spacing: 2px;
}
b{
	color: mediumblue;
}
hr { 
    display: block;
    margin-top: 0;
    margin-bottom: 0;
    margin-left: auto;
    margin-right: auto;
    border-style: inset;
    border-width: 1px;
} 
</style>
<title></title>
</head>
<body>
<table align="center" style="border: 1px solid brown; background-color: white;" border="0" cellpadding="0" cellspacing="0"
    width="550px">
    <tbody>
        <tr>
            <td>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tbody>
                        <tr>
                            <td bgcolor="brown" style="width:20%; height: 82px;  font-family: Arial; font-weight: bold; 
                            font-size: 32px; color: #efe9e5; line-height: 36px; text-align: center;">
                           		<img alt="Facility Management Solutions" style="font-size: 10px; color: white; font-style: italic; font-weight: bold;" 
                           		src="${organizationURL}/images/logo_white_small.png" width="96" height="48"/>   
                            </td>
                            <td bgcolor="brown" style="width:55%; height: 82px;  font-family: Arial; font-weight: bold; 
                            font-size: 32px; color: #efe9e5; line-height: 36px; text-align: center;">
                           		 ${organizationName}
                            </td>
                            <td bgcolor="gray" >
                                <div align="center"><font color="white" size="4" align="center">Inspection solutions</font></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <hr>
            </td>
        </tr>    
        <tr>
            <td valign="top" >
                <table border="0" cellpadding="0" cellspacing="0"
                    width="100%">
                    <tbody>

                        <tr>
                            <td align="left" valign="top">
                                <table style=" cellpadding="0"
                                    cellspacing="0" width="100%" align="center">
                                    <tbody>
                                        <tr>
                                            <td bgcolor="black" height="30" valign="top" align="center"><font color="white" size="4" align="center"><i>Server Error Email</i></font></td>
                                        </tr>
                                        <tr>
                                            <td style="height: 20px; line-height: 0;" height="20"></td>
                                        </tr>
                                        <tr>
                                            <td valign="top"><table cellpadding="0" cellspacing="0">
                                            	<table>
                                                    <tbody>                                                    
                                                        <tr>
                                                            <td width="13"></td>
                                                            <td style="font-family: Arial, Helvetica, sans-serif; color: #2c0000; font-size: 12px; ">
                                                                ${message}
															</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                             </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                    <tbody>
                        <tr>
                            <td style="background: brown; height: 30px;" height="30"
                                valign="top"><table style="height: 30px;" border="0"
                                    cellpadding="0" cellspacing="0" width="100%" align="center">
                                    <tbody>
                                        <tr>
                                           <td
                                                style="border-right: 1px solid #FFFFFF; text-align: center; height: 30px;"
                                                align="center" height="30" width="33%"><a
                                                target="_blank"
                                                name="Privacy"
                                                href="${PrivacyURL}"
                                                style="font-family: Verdana; font-size: 10px; color: #ffffff; text-decoration: underline; font-weight: bold; line-height: 10px;">Privacy Policy</a>
                                            </td>
                                            <td
                                                style="border-right: 1px solid #FFFFFF; text-align: center; height: 30px;"
                                                align="center" height="30" width="33%"><a
                                                target="_blank"
                                                name="Unsubscribe"
                                                href="${UnsubscribeURL}"
                                                style="font-family: Verdana; font-size: 10px; color: #ffffff; text-decoration: underline; font-weight: bold; line-height: 10px;">Unsubscribe</a>
                                            </td>                                            
                                            <td style="text-align: center; height: 30px;" align="center"
                                                height="30" width="33%"><a target="_blank"
                                                name="Organization"
                                                href="${organizationURL}"
                                                style="font-family: Verdana; font-size: 10px; color: #ffffff; text-decoration: underline; font-weight: bold; line-height: 10px;">${organizationURL}</a>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                              </td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        <tr>
            <td valign="top" align="center">
                <center>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tbody>
                            <tr>
                                <td
                                    style=" font-family: Arial, Helvetica, sans-serif; font-weight: normal; color: #888888; font-size: 9px;"
                                    align="center"><br/><br/>${copyRightText}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </center>
            </td>
        </tr>
    </tbody>
</table>
</body>
</html>
