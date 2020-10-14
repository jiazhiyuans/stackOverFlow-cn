package com.ali.edu.stackoverflow.zdemo;

import com.ali.edu.stackoverflow.entity.DeviceAddParam;
import com.ali.edu.stackoverflow.utils.HttpUtils;
import com.ali.edu.stackoverflow.utils.entity.FactorCategory;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

/**
 * @author rocky
 * @Description:
 * @Date: 2020-09-23
 * @Time: 15:40
 */
public class Main {
    public static void main(String[] args) {
        String[] tableHeaders = {"id", "姓名", "年龄"};

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = workbook.createFont();
//        font.setColor(HSSFColor.RED.index);
        font.setBold(true);
        cellStyle.setFont(font);

        // 将第一行的三个单元格给合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        HSSFRow row = sheet.createRow(0);
        HSSFCell beginCell = row.createCell(0);
        beginCell.setCellValue("通讯录");
        beginCell.setCellStyle(cellStyle);

        row = sheet.createRow(1);
        // 创建表头
        for (int i = 0; i < tableHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(tableHeaders[i]);
            cell.setCellStyle(cellStyle);
        }

        List<User> users = new ArrayList<>();
        users.add(new User(1L, "张三", 20));
        users.add(new User(2L, "李四", 21));
        users.add(new User(3L, "王五", 22));

        for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);

            User user = users.get(i);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getAge());
        }
    }

    @Test
    public void testaddManufacture(){
        List<String> list = new ArrayList<>();
        int count = 0;
        HttpUtils  httpUtils = new HttpUtils();
        HashSet<String> hashSet =
                ExcelUtils.poiReadExcelReadManufacture("C:\\Users\\rocky\\Desktop\\产品导入数据整理\\制造商整理.xlsx");
        for(String s : hashSet){
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setManufacturerCode(ExcelUtils.generateShortUuid());
            manufacturer.setManufacturerName(s);
            Boolean res = httpUtils.postAddManufacture("http://bmp-yanshi.fpi-inc.site/bmp-device-manage-server/api/v1/manufacturer/add", manufacturer);
            if(res == true){
                count ++;
            }else {
                list.add(s);
            }
        }
        System.out.println(list);
        System.out.println(count);
    }

    @Test
    public void insertFactorCategory(){
        HttpUtils  httpUtils = new HttpUtils();
        List<FactorIndex> factorAIndexList = ExcelUtils.poiReadExcelgetFactorIndex("C:\\Users\\rocky\\Desktop\\数据\\大气污染物名称代码.xlsx");
        List<FactorIndex> factorWIndexList = ExcelUtils.poiReadExcelgetFactorIndex("C:\\Users\\rocky\\Desktop\\数据\\水污染物名称代码.xlsx");
        HashMap<String,String> map = new HashMap<>();
        for (FactorIndex factorIndex : factorAIndexList) {
            map.put(factorIndex.getName(),factorIndex.getCode());
        }
        for (FactorIndex factorIndex : factorWIndexList) {
            map.put(factorIndex.getName(),factorIndex.getCode());
        }
//        List<DeviceAddParam> DeviceAddParamList = ExcelUtils.poiReadExcelAndReturnDevice("C:\\Users\\rocky\\Desktop\\产品导入数据整理\\大气\\大气设备类型整理.xlsx",map);
//        for (DeviceAddParam deviceAddParam : DeviceAddParamList) {
//            httpUtils.postAddDevice("http://hangzhou-test.fpi-inc.site/bmp-device-manage-server/api/v1/device/add-device",deviceAddParam);
//        }
    }

    @Test
    public void importData(){
        List<String> list = new ArrayList<>();
        int count = 0;
        HttpUtils  httpUtils = new HttpUtils();
        HashSet<String> hashSet =
                ExcelUtils.poiReadExcelReadManufacture("C:\\Users\\rocky\\Desktop\\产品导入数据整理\\制造商整理.xlsx");

        Map<String, String> stringStringMap = ExcelUtils.poiReadExcelReadTransform("C:\\Users\\rocky\\Desktop\\大气设备因子\\因子.xlsx");
        List<DeviceAddParam> DeviceAddParamList = ExcelUtils.poiReadExcelAndReturnDevice("C:\\Users\\rocky\\Desktop\\大气设备因子\\设备1.xlsx",stringStringMap,hashSet);
        for (DeviceAddParam deviceAddParam : DeviceAddParamList) {
            String s = JSONObject.toJSONString(deviceAddParam);
            boolean res = httpUtils.postAddDevice("http://bmp-yanshi.fpi-inc.site/bmp-device-manage-server/api/v1/device/add-device",deviceAddParam);
            if(res == true){
                count++;
            }else{
                list.add(deviceAddParam.getDeviceCommonNameAttrs().getDeviceCode());
            }
        }
        System.out.println(list);
    }

    @Test
    public void importData2(){
        int count = 0;
        List<String> list = new ArrayList();
        HttpUtils  httpUtils = new HttpUtils();
        List<DeviceAddParam> DeviceAddParamList = ExcelUtils.poiReadExcelAndReturnWaterDevice
                ("C:\\Users\\rocky\\Desktop\\产品导入数据整理\\水质监测设备2.xlsx");
        for (DeviceAddParam deviceAddParam : DeviceAddParamList) {
            String s = JSONObject.toJSONString(deviceAddParam);
            boolean res = httpUtils.postAddDevice("http://hangzhou-test.fpi-inc.site/bmp-device-manage-server/api/v1/device/add-device",deviceAddParam);
            if(res == true){
                count++;
            }else{
                list.add(deviceAddParam.getDeviceCommonNameAttrs().getDeviceCode());
            }
        }
        System.out.println(list);
        System.out.println(count);
    }
}
