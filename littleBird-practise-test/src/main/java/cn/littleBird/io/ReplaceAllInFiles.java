package cn.littleBird.io;

import java.io.*;

public class ReplaceAllInFiles {
  public static void replaceAllInFile(File Dir, String str1, String str2) throws Exception {
//    File file = new File("E:\\temp\\advsearch");
    File[] files = Dir.listFiles();
    for(File _file : files){
      if (_file.isDirectory()) {
        System.out.println(_file.getName() + "目录: ");
        replaceAllInFile(_file, str1, str2);
      } else {
        System.out.println(_file.getName());
        replace(_file, str1, str2);
      }
    }
  }
  public static void replace(File file, String str1, String str2) throws Exception {
    FileReader fileReader = new FileReader(file);
    StringBuffer stringBuffer = new StringBuffer();
    char[] chars = new char[1024];
    int readLen;
    while ((readLen = fileReader.read(chars)) > 0){
      stringBuffer.append(chars, 0, readLen);
    }
//    System.out.println(stringBuffer.toString());
    System.out.println(file.getParentFile());
    File temp = new File(file.toString().replaceAll("\\\\", "\\\\\\\\").replace("temp", "temp\\\\\\\\replace path"));
    if(!temp.exists() || temp.getParentFile().isDirectory()){
      if(!temp.getParentFile().exists()){
        temp.getParentFile().mkdirs();
      }
      temp.createNewFile();
    }
    FileWriter fileWriter = new FileWriter(temp);
//    fileWriter.write(stringBuffer.toString().replaceAll(str1, str2));
    fileWriter.write(stringBuffer.toString()
        .replaceAll("更新日期", "{{ t('search.lable_updated_date') }}")
        .replaceAll("序号", "{{ t('search.no') }}")
        .replaceAll("来源", "{{ t('search.result_filds_database') }}")
        .replaceAll("result_filds", "result_fields")
        .replaceAll("实施日期", "{{ t('search.lable_implementation_date') }}")
        .replaceAll("发布日期", "{{ t('search.result_fields_issuance_date') }}")
        .replaceAll("发布机关", "{{ t('search.result_fields_publisher') }}")
        .replaceAll("年份", "{{ t('search.result_year') }}")
        .replaceAll("年", "this.t('search.result_year')")
        .replaceAll("刊名", "{{ t('search.cjfq_result_fields_journal') }}")
        .replaceAll("年期", "{{ t('search.cjfq_result_fields_year_date') }}")
        .replaceAll("时效性", "{{ t('search.result_fields_timeliness') }}")
        .replaceAll("专利名称", "{{ t('search.result_fields_patent_name') }}")
        .replaceAll("发明人", "{{ t('search.result_fields_inventor') }}")
        .replaceAll("申请人", "{{ t('search.result_fields_applicant') }}")
        .replaceAll("公开日", "{{ t('search.result_fields_open_day') }}")
        .replaceAll("导师", "{{ t('search.result_fields_supervisor') }}")
        .replaceAll("标准名称", "{{ t('search.result_fields_standard_name') }}")
        .replaceAll("标准号", "{{ t('search.result_fields_publication_date') }}")
        .replaceAll("审理法院", "{{ t('search.result_fields_trial_court') }}")
        .replaceAll("裁判日期", "{{ t('search.result_fields_judge_date') }}")
        .replaceAll("争议焦点", "{{ t('search.result_fields_controversial_focus') }}")
        .replaceAll("指导", "{{ t('search.result_fields_guide') }}")
        .replaceAll("法官", "{{ t('search.result_fields_judge') }}")
        .replaceAll("发文字号", "{{ t('search.result_fields_text_number') }}")
        .replaceAll("效力级别", "{{ t('search.result_fields_potency_level') }}")
        .replaceAll("条目概要", "{{ t('search.result_fields_item_summary') }}")
        .replaceAll("条目来源", "{{ t('search.result_fields_source_of_entry') }}")
    );
    fileWriter.close();
    fileReader.close();
  }

  public static void main(String[] args) throws Exception {
    replaceAllInFile(new File("E:\\temp\\skin"), "", "");
  }
}
