package com.personal.info.myInfoSystem;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class PDFUntilTest {

    /*public static void main(String[] args){
        //创建文档，添加PDF页面
        PdfDocument pdf = new PdfDocument();
        PdfPageBase page0 = pdf.getPages().add();
        PdfPageBase page = pdf.getPages().add();
        PdfPageBase page1 = pdf.getPages().add();

        PdfPageBase page2 = pdf.getPages().add();
        pdf.getPages().remove(page0);

        //创建PdfGrid对象
        PdfGrid grid = new PdfGrid();
        //设置单元格内边距、默认字体、字体颜色和默认背景色
        grid.getStyle().setCellPadding(new PdfPaddings(3,3,3,3));
        //Arial Unicode MS
        grid.getStyle().setFont(new PdfTrueTypeFont(new Font("Arial Unicode MS", Font.PLAIN,10), true));
        grid.getStyle().setTextBrush(PdfBrushes.getBlack());

        //创建PdfBorders对象，并设置颜色及粗细
        PdfBorders borders= new PdfBorders();
        borders.setAll(new PdfPen(PdfBrushes.getWhite(),1f));

        //定义数据
        String[] data = {"学校;Logo;code;address",
                "sichuan;;10610;chendu",
                "jiaotongdaxue;;10613;chendu",
                "dianzikejidaxue;;10614;chendu",
                "xinanshiyoudaxue;;10615;chendu",
        };
        String[][] dataSource = new String[data.length][];
        for (int i = 0; i < data.length; i++) {
            dataSource[i] = data[i].split("[;]");
        }

        //填充数据到表格
        grid.setDataSource(dataSource);

        //在表格第2列填充图片并设置列宽
        grid.getRows().get(1).getCells().get(1).getStyle().setBackgroundImage(PdfImage.fromFile("C:\\Users\\Admin\\Desktop\\blog_picture\\1.png"));
        grid.getRows().get(2).getCells().get(1).getStyle().setBackgroundImage(PdfImage.fromFile("C:\\Users\\Admin\\Desktop\\blog_picture\\2.jpeg"));
        grid.getRows().get(3).getCells().get(1).getStyle().setBackgroundImage(PdfImage.fromFile("C:\\Users\\Admin\\Desktop\\blog_picture\\3.jpeg"));
        grid.getRows().get(4).getCells().get(1).getStyle().setBackgroundImage(PdfImage.fromFile("C:\\Users\\Admin\\Desktop\\blog_picture\\4.jpg"));
        grid.getColumns().get(1).setWidth(100f);

        //纵向合并单元格
        grid.getRows().get(1).getCells().get(3).setRowSpan(4);

        //设置表格
        for (int i = 0; i < data.length ; i++) {
            //设置每一行的高度
            grid.getRows().get(i).setHeight(50f);
            //设置第一列的字体
            grid.getRows().get(i).getCells().get(0).getStyle().setFont(new PdfTrueTypeFont(new Font("Arial Unicode MS",Font.PLAIN,12),true));

            for(int j =0;j<grid.getColumns().getCount();j++){
                //设置所有单元格居中
                grid.getRows().get(i).getCells().get(j).setStringFormat(new PdfStringFormat(PdfTextAlignment.Center,PdfVerticalAlignment.Middle));
                //设置第一行的背景色
                grid.getRows().get(0).getCells().get(j).getStyle().setBackgroundBrush(PdfBrushes.getBeige());
            }
        }
        //绘制表格到PDF
        grid.draw(page,0,30);

        //保存文档
        pdf.saveToFile("添加表格.pdf");
        pdf.close();
        System.out.println("xxx");
    }*/

    /*public static void main(String[] args) throws FileNotFoundException, IOException {

        //创建PdfDocument对象
        PdfDocument doc = new PdfDocument();

        //添加一页
        PdfPageBase page = doc.getPages().add();

        //标题文字
        String title = "标题";


        //创建单色画刷对象`
        PdfSolidBrush brush1 = new PdfSolidBrush(new PdfRGBColor(Color.BLUE));
        PdfSolidBrush brush2 = new PdfSolidBrush(new PdfRGBColor(Color.BLACK));

//        Font font = new Font("Arial", 10,Font.PLAIN, PdfGraphicsUnit.Point, 1, true);

        Font font = new Font("Arial", Font.BOLD, 12);


        //创建TrueType字体对象
        PdfTrueTypeFont font1= new PdfTrueTypeFont(font,true);
        PdfTrueTypeFont font2= new PdfTrueTypeFont(new Font("Arial Unicode MS",Font.BOLD,10),true);

        //创建PdfStringFormat对
        PdfStringFormat format1 = new PdfStringFormat();
        format1.setAlignment(PdfTextAlignment.Center);//设置文字居中
        //int a = page.getActualBounds(true).getWidth();
        //使用drawString方法绘制标题文字
        page.getCanvas().drawString(title, font1, brush1, new Point2D.Double(page.getActualBounds(true).getWidth()/ 2, 0),format1);

        //从txt文件读取内容到字符串
        String body = readFileToString("C:\\Users\\Admin\\Desktop\\bodyText.txt");

        //创建PdfStringFormat对象
        PdfStringFormat format2 = new PdfStringFormat();
        format2.setParagraphIndent(20);//设置段首缩进

        //创建Rectangle2D对象
        Rectangle2D.Double rect = new Rectangle2D.Double(0, 30, page.getActualBounds(true).getWidth(),page.getActualBounds(true).getHeight());

        //使用drawString方法在矩形区域绘制主体文字
        page.getCanvas().drawString(body, font2, brush2, rect,format2);

        //保存到PDF文档
        doc.saveToFile("ouput.pdf", FileFormat.PDF);
    }
*/
    //自定义方法读取txt文件内容到字符串
    private static String readFileToString(String filepath) throws FileNotFoundException, IOException {

        StringBuilder sb = new StringBuilder();
        String s ="";
        BufferedReader br = new BufferedReader(new FileReader(filepath));

        while( (s = br.readLine()) != null) {
            sb.append(s + "\n");
        }
        br.close();
        String str = sb.toString();
        return str;
    }

    /**
     * 生成PDF文件
     * D:\pdfile\
     */
    public static boolean generatePdfFile(String pdf_url,List list){
        boolean result = true;
        com.itextpdf.text.Rectangle rectPageSize = new com.itextpdf.text.Rectangle(PageSize.A4);//A4纸张
        //rectPageSize.setBackgroundColor(BaseColor.ORANGE);
        Document document = new Document(rectPageSize, 40, 40, 40, 40);//上、下、左、右间距
        try {
            //Date date = new Date();
            pdf_url = pdf_url+ ".pdf";
            System.out.println("文件存储路劲："+pdf_url);
            //创建一个PdfWriter实例
            //将文件输出流指向一个文件
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(pdf_url));
            //PDF版本(默认1.4)
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_4);

            //文档属性
            document.addTitle("Title@sample");
            document.addAuthor("Author@huangshuang");
            document.addSubject("Subject@iText sample");
            document.addKeywords("Keywords@iText");
            document.addCreator("Creator@iText");

            //页边空白
            //document.setMargins(10, 20, 30, 40);

            document.open();
            //document.add(new Paragraph("Hello World"));
            Map<String,Object> a=(Map<String,Object>)list.get(0);
            /*for (int i=0;i<list.size();i++){
                Map<String,Object> a=(Map<String,Object>)list.get(i);
                Map<String,Object> map = new HashMap<>();
                map.
            }*/

            //打开文档。
            //document.open();

            //在文档中增加一个段落
            //解决中文乱码
            document.add(new com.itextpdf.text.Paragraph(a.toString(),new com.itextpdf.text.Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED),14, com.itextpdf.text.Font.NORMAL)));

        } catch (DocumentException de) {
            result = false;
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            result = false;
            System.err.println(ioe.getMessage());
        }
        //关闭文档。
        document.close();
        return result;
    }

    public static void main(String[] args)  {
        String data = "#在抖音，记录美好生活#这大概就是冰雪美人吧…… http://v.douyin.com/eUWYth/ 复制此链接，打开【抖音短视频】，直接观看视频！";
        Matcher matcher = Patterns.WEB_URL.matcher(data);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        String s="[](http://res.eval.jyjy.cn/FhxD9sStGedeXtw_VmUSVfgGAZpN)";
        String STR = s.substring(3,s.length()-1);
        System.out.println(STR);
    }
}
