/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*/
function refreshProgress()
{
    UploadMonitor.getUploadInfo(updateProgress,'');
}

function refreshProgress2()
{
    UploadMonitor.getUploadInfo(updateProgress2);
}

function refreshProgress3()
{
    UploadMonitor.getUploadInfo(updateProgress3);
}

function updateProgress(uploadInfo)
{
    if (uploadInfo.inProgress)
    {
        document.getElementById('uploadbutton').disabled = true;
        document.getElementById('userfile1').disabled = true;
     

        var fileIndex = uploadInfo.fileIndex;

        var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);

        document.getElementById('progressBarText').innerHTML = 'טוען קובץ לשרת: <font color="green">' + progressPercent + '%</font>';

        document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';

        window.setTimeout('refreshProgress()', 50);
    }
    else
    {
        //document.getElementById('uploadbutton').disabled = false;
        //document.getElementById('userfile1').disabled = false;
        
        document.getElementById('progressBarText').innerHTML = 'טעינת קובץ הושלמה.<br> בודק תקינות ופורס מידע מחדש.<br> אנא המתן...';
       
    }

    return true;
}

function updateProgress2(uploadInfo)
{
    if (uploadInfo.inProgress)
    {
        document.getElementById('uploadbutton2').disabled = true;
        document.getElementById('userfile2').disabled = true;
     

        var fileIndex = uploadInfo.fileIndex;

        var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);

        document.getElementById('progressBarText2').innerHTML = 'טוען קובץ לשרת: <font color="green">' + progressPercent + '%</font>';

        document.getElementById('progressBarBoxContent2').style.width = parseInt(progressPercent * 3.5) + 'px';

        window.setTimeout('refreshProgress2()', 50);
    }
    else
    {
        //document.getElementById('uploadbutton2').disabled = false;
        //document.getElementById('userfile2').disabled = false;
        document.getElementById('progressBarText2').innerHTML = 'טעינת קובץ הושלמה.<br> בודק תקינות ופורס מידע מחדש.<br> אנא המתן...';
       
    }

    return true;
}

function updateProgress3(uploadInfo)
{
    if (uploadInfo.inProgress)
    {
        document.getElementById('uploadbutton3').disabled = true;
        document.getElementById('userfile3').disabled = true;
     

        var fileIndex = uploadInfo.fileIndex;

        var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);

        document.getElementById('progressBarText3').innerHTML = 'טוען קובץ לשרת: <font color="green">' + progressPercent + '%</font>';

        document.getElementById('progressBarBoxContent3').style.width = parseInt(progressPercent * 3.5) + 'px';

        window.setTimeout('refreshProgress3()', 50);
    }
    else
    {
        //document.getElementById('uploadbutton3').disabled = false;
        //document.getElementById('userfile3').disabled = false;
        document.getElementById('progressBarText3').innerHTML = 'טעינת קובץ הושלמה.<br> בודק תקינות ופורס מידע מחדש.<br> אנא המתן...';
       
    }

    return true;
}

function startProgress()
{
		
    document.getElementById('progressBar').style.display = 'block';
    document.getElementById('progressBarText').innerHTML = 'טוען קובץ לשרת: <font color="red">0%</font>';
    document.getElementById('uploadbutton').disabled = true;

    // wait a little while to make sure the upload has started ..
    window.setTimeout("refreshProgress()", 1500);
   

    return true;
}

function startProgress2()
{
		
    document.getElementById('progressBar2').style.display = 'block';
    document.getElementById('progressBarText2').innerHTML = 'טוען קובץ לשרת: <font color="red">0%</font>';
    document.getElementById('uploadbutton2').disabled = true;

    // wait a little while to make sure the upload has started ..
    window.setTimeout("refreshProgress2()", 1000);
   

    return true;
}

function startProgress3()
{
		
    document.getElementById('progressBar3').style.display = 'block';
    document.getElementById('progressBarText3').innerHTML = 'טוען קובץ לשרת: <font color="red">0%</font>';
    document.getElementById('uploadbutton3').disabled = true;

    // wait a little while to make sure the upload has started ..
    window.setTimeout("refreshProgress3()", 1000);
   

    return true;
}


