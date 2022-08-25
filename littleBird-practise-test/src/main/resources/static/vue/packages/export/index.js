import htmlDocx from 'html-docx-js/dist/html-docx';
import saveAs from "file-saver";


/** 
 * todo sjy 
 * @dom ref对象，传入的要导出页面的最外层ref的clone对象
 * @hasEchart boolean 传入是否存在echarts图表的判断
 * @imgSrc string 如果hasEchart为true，则需要传入imgSrc参数，改参数为localStroge中的imgSrc,存放了
 * @txt css样式文本引入对象（必传）
 * */
function exportDocx(dom, hasEchart, imgSrc, txt) {
  // 克隆报告HTML
  var contentDocument = dom;
  convertImagesToBase64(contentDocument);
  convertChartsToBase64(contentDocument, hasEchart, imgSrc);
  var content = `<!DOCTYPE html><html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <style>
                ${txt}
            </style>
        </head>
        <body>
            ${contentDocument.innerHTML}
        </body>
        </html>`;
  var converted = htmlDocx.asBlob(content);
  return converted;
}
// 转换图片为 base64 (todo: 有可能因跨域报错)
function convertImagesToBase64(contentDocument) {
  // 找到所有的图片
  var imgs = contentDocument.querySelectorAll("img");
  // 图片转换用
  var canvas = document.createElement("canvas");
  var ctx = canvas.getContext("2d");

  imgs.forEach((img, i) => {
    if (img.src.startsWith("data:image")) return;

    // img表现尺寸
    var realWidth = parseInt(img.style.width);
    var realHeight = parseInt(img.style.height);
    // 清空画布并调整为 img 的大小
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    canvas.width = realWidth;
    canvas.height = realHeight;

    // 画图片到画布
    ctx.drawImage(
      img,
      0,
      0,
      img.width,
      img.height,
      0,
      0,
      realWidth,
      realHeight
    );
    // 画布转为 base64
    var dataURL = canvas.toDataURL();
    img.setAttribute("src", dataURL);
  });

  canvas.remove();
}

function convertChartsToBase64(contentDocument, hasEchart, imgSrc) {
  // 找到所有的图表 （echart）
  var canvases = contentDocument.querySelectorAll("canvas");
  // 遍历图表，转换为 base64 静态图片
  canvases.forEach((canvas, i) => {
    if (hasEchart) {
      var url = imgSrc;
      var img = document.createElement("img");
      img.src = url;
      canvas.parentNode.replaceChild(img, canvas);
    }
  });
}

//不使用JQuery版的
import html2canvas from 'html2canvas';
import {
  jsPDF
} from "jspdf";

/**
 * @param  dom  要生成 pdf 的DOM元素（容器）
 * @param  padfName  PDF文件生成后的文件名字
 * */
function exportPdf(dom, pdfName) {
  var ele = dom;
  var canvas = document.createElement("canvas");

  canvas.width = ele.offsetWidth;
  canvas.height = ele.offsetHeight;
  html2canvas(document.body, {
    x: ele.offsetLeft,
    y: ele.offsetTop,
    width: ele.offsetWidth,
    height: ele.offsetHeight
  }).then(function (canvas) {
    var contentWidth = canvas.width;
    var contentHeight = canvas.height;
    var pageHeight = contentWidth / 595.28 * 841.89; //一页pdf显示html页面生成的canvas高度;
    var leftHeight = contentHeight; //未生成pdf的html页面高度
    var position = 0; //页面偏移
    var imgWidth = 595.28; //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
    var imgHeight = 595.28 / contentWidth * contentHeight;
    var pageData = canvas.toDataURL('image/jpeg', 1.0);
    var pdf = new jsPDF('', 'pt', 'a4');
    //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
    //当内容未超过pdf一页显示的范围，无需分页
    if (leftHeight < pageHeight) {
      pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight);
    } else {
      while (leftHeight > 0) {
        pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
        leftHeight -= pageHeight;
        position -= 841.89;
        //避免添加空白页
        if (leftHeight > 0) {
          pdf.addPage();
        }
      }
    }
    pdf.save(pdfName);
  });
}

export default {
  exportDocx,
  exportPdf
}
