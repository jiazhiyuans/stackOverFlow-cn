package com.ali.edu.stackoverflow.zdemo;

import com.ali.edu.stackoverflow.entity.*;
import com.ali.edu.stackoverflow.utils.entity.FactorCategory;
import com.ali.edu.stackoverflow.utils.entity.UUIDGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author rocky
 * @Description:
 * @Date: 2020-09-23
 * @Time: 15:51
 */
public class ExcelUtils {

    public static List<DeviceAddParam> poiReadExcelAndReturnDevice(String filePath, Map<String, String> map, HashSet<String> set) {
        File file = new File(filePath);
        try {
            FileInputStream stream = FileUtils.openInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            //获取第一个表单
            XSSFSheet sheet = workbook.getSheetAt(0);
            //获取sheet中最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            List<DeviceAddParam> deviceAddParamList = new ArrayList<>();
            for (int i = 1; i <= lastRowNum; i++) {
                DeviceCommonNameAttrs deviceCommonNameAttrs = new DeviceCommonNameAttrs();
                //获取一条数据
                XSSFRow row = sheet.getRow(i);

                XSSFCell deviceCodeCell = row.getCell(0);
                //deivceCode
                if (null != deviceCodeCell) {
                    deviceCodeCell.setCellType(CellType.STRING);
                    String factorNameValue = deviceCodeCell.getStringCellValue();
                    deviceCommonNameAttrs.setDeviceCode(String.valueOf(factorNameValue));
                }
                XSSFCell manufactureCell = row.getCell(2);
                if (null != manufactureCell) {
                    manufactureCell.setCellType(CellType.STRING);
                    String manufactureValue = manufactureCell.getStringCellValue();
                    if(set.contains(manufactureValue)){
                        deviceCommonNameAttrs.setManufacturerName(String.valueOf(manufactureValue));
                    }else{
                        deviceCommonNameAttrs.setManufacturerName(String.valueOf("其他"));
                    }
                }
                XSSFCell deviceTypeCell = row.getCell(1);
                if (null != deviceTypeCell) {
                    deviceTypeCell.setCellType(CellType.STRING);
                    String deviceTypeNameCell = deviceTypeCell.getStringCellValue();
                    deviceCommonNameAttrs.setDeviceTypeName(String.valueOf(deviceTypeNameCell));
                }

                //基本属性
                //设备名称
                XSSFCell deviceNameCell = row.getCell(3);
                Map<String, DeviceBasicAttrNameValueVo> basicAttrVoMap = new HashMap<>();
                if (null != deviceNameCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    deviceNameCell.setCellType(CellType.STRING);
                    String deviceName = deviceNameCell.getStringCellValue();
                    //to do fuck pm
                    deviceBasicAttrNameValueVo.setBasicAttrValue(deviceName);
                    basicAttrVoMap.put("deviceName", deviceBasicAttrNameValueVo);
                }

                //设备说明
                XSSFCell descripitionCell = row.getCell(4);
                if (null != descripitionCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    descripitionCell.setCellType(CellType.STRING);
                    String descripition = descripitionCell.getStringCellValue();
                    //to do fuck pm
                    deviceBasicAttrNameValueVo.setBasicAttrValue(descripition);
                    basicAttrVoMap.put("description", deviceBasicAttrNameValueVo);
                }

                //设备分类
                XSSFCell categoryCell = row.getCell(5);
                if (null != categoryCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    categoryCell.setCellType(CellType.STRING);
                    String categoryValue = categoryCell.getStringCellValue();
                    //to do fuck pm
                    deviceBasicAttrNameValueVo.setBasicAttrValue(categoryValue);
                    basicAttrVoMap.put("category", deviceBasicAttrNameValueVo);
                }

                //采集周期
                XSSFCell acquisitionCycleCell = row.getCell(6);
                if (null != acquisitionCycleCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    acquisitionCycleCell.setCellType(CellType.STRING);
                    String acquisitionCycleValue = acquisitionCycleCell.getStringCellValue();
                    //to do fuck pm
                    deviceBasicAttrNameValueVo.setBasicAttrValue(acquisitionCycleValue);
                    basicAttrVoMap.put("acquisitionCycle", deviceBasicAttrNameValueVo);
                }
                //通道号
                XSSFCell channelNumberCell = row.getCell(8);
                if (null != acquisitionCycleCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    channelNumberCell.setCellType(CellType.STRING);
                    String channelNumberValue = channelNumberCell.getStringCellValue();
                    //to do fuck pm
                    deviceBasicAttrNameValueVo.setBasicAttrValue(channelNumberValue);
                    basicAttrVoMap.put("channelNumber", deviceBasicAttrNameValueVo);
                }
                //通讯类型
                XSSFCell communicationTypeCell = row.getCell(9);
                if (null != acquisitionCycleCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    communicationTypeCell.setCellType(CellType.STRING);
                    String communicationTypeValue = communicationTypeCell.getStringCellValue();
                    //to do fuck pm
                    deviceBasicAttrNameValueVo.setBasicAttrValue(communicationTypeValue);
                    basicAttrVoMap.put("communicationType", deviceBasicAttrNameValueVo);
                }
                //通讯参数
                XSSFCell CommunicationParametersCell = row.getCell(10);
                if (null != acquisitionCycleCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    CommunicationParametersCell.setCellType(CellType.STRING);
                    String CommunicationParametersValue = CommunicationParametersCell.getStringCellValue();
                    //to do fuck pm
                    deviceBasicAttrNameValueVo.setBasicAttrValue(CommunicationParametersValue);
                    basicAttrVoMap.put("communicationParameters", deviceBasicAttrNameValueVo);
                }
                Map<String, DeviceExtendAttrNameValueVo> extendAttrVoMap = new HashMap<>();
                //扩展属性
                XSSFCell measureCodeCell = row.getCell(7);
                if (null != measureCodeCell) {
                    measureCodeCell.setCellType(CellType.STRING);
                    String measureCodeCellValue = measureCodeCell.getStringCellValue();
                    if(!StringUtils.isEmpty(measureCodeCellValue)){
                        List<Map<String, DeviceSubAttrNameValueVo>> extendAttrValuesMapList = new ArrayList<>();
                        //获取数据

                        String[] split = measureCodeCellValue.split(",");
                        List<String> arrList = new ArrayList<>();
                        for (String s : split){
                            String s1 = map.get(s);
                            if(!StringUtils.isEmpty(s1)){
                                arrList.add(s1);
                            }
                        }
                        for (String s : arrList) {
                            Map<String, DeviceSubAttrNameValueVo> subFactorNameMap = new HashMap<>();
                            DeviceSubAttrNameValueVo deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                            deviceSubAttrNameValueVo.setSubAttrValue(s);
                            subFactorNameMap.put("factorCode", deviceSubAttrNameValueVo);
                            extendAttrValuesMapList.add(subFactorNameMap);
                        }
                        DeviceExtendAttrNameValueVo deviceExtendAttrNameValueVo = new DeviceExtendAttrNameValueVo();
                        deviceExtendAttrNameValueVo.setExtendAttrValuesMapList(extendAttrValuesMapList);
                        extendAttrVoMap.put("factors", deviceExtendAttrNameValueVo);
                    }
                }
                DeviceAddParam deviceAddParam = new DeviceAddParam();
                deviceAddParam.setDeviceCommonNameAttrs(deviceCommonNameAttrs);
                deviceAddParam.setBasicAttrVoMap(basicAttrVoMap);
                deviceAddParam.setExtendAttrVoMap(extendAttrVoMap);
                deviceAddParamList.add(deviceAddParam);
            }
            return deviceAddParamList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将制造商名称放入HashSet
     *
     * @param
     * @return
     */
    public static HashSet<String> poiReadExcelReadManufacture(String filePath) {
        int repeat = 0;
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = FileUtils.openInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            //获取第一个表单
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            HashSet<String> set = new HashSet<>();
            for (int i = 1; i < lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(0);
                cell.setCellType(CellType.STRING);
                String stringCellValue = cell.getStringCellValue();
                if(set.contains(stringCellValue)){
                    repeat++;
                    continue;
                }
                set.add(stringCellValue);
            }
            System.out.println(String.format("一共%d, 重复%d条",lastRowNum,repeat));
            return set;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<FactorIndex> poiReadExcelgetFactorIndex(String url) {

        List<FactorIndex> list= new ArrayList<>();
        try {
            File file = new File(url);
            FileInputStream fileInputStream = FileUtils.openInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            //获取第一个表单
            XSSFSheet sheet = workbook.getSheetAt(1);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);

                FactorIndex factorIndex = new FactorIndex();
                XSSFCell codeCell = row.getCell(0);
                codeCell.setCellType(CellType.STRING);
                String codeCellValue = codeCell.getStringCellValue().trim();
                factorIndex.setCode(codeCellValue);

                XSSFCell chineseNameCell = row.getCell(2);
                chineseNameCell.setCellType(CellType.STRING);
                String chineseNameCellValue = chineseNameCell.getStringCellValue().trim();
                factorIndex.setName(chineseNameCellValue);
                factorIndex.setId(UUIDGenerator.getUUID());
                list.add(factorIndex);
            }
            return  list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<FactorCategory> poiReadExcelgetFactorCategory(String filePath) {
        List<FactorCategory> list= new ArrayList<>();
        FactorCategory parent = new FactorCategory();
        parent.setId("3333ae2734d64bbe8b598af6c6610003");
        int repeat = 0;
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = FileUtils.openInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            //获取第一个表单
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            HashSet<String> set = new HashSet<>();
            for (int i = 1; i < lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell codeCell = row.getCell(0);
                codeCell.setCellType(CellType.STRING);
                String codeCellValue = codeCell.getStringCellValue();
                XSSFCell ChineseName = row.getCell(1);
                ChineseName.setCellType(CellType.STRING);
                String chineseNameValue = ChineseName.getStringCellValue();
                XSSFCell descriptionCell = row.getCell(2);
                descriptionCell.setCellType(CellType.STRING);
                String descriptionCellValue = descriptionCell.getStringCellValue();
                FactorCategory factorCategory = new FactorCategory();
                factorCategory.setCode(codeCellValue);
                factorCategory.setDescription(descriptionCellValue);
                factorCategory.setName(chineseNameValue);
                factorCategory.setParent(parent);
                factorCategory.setParentId("3333ae2734d64bbe8b598af6c6610003");
                list.add(factorCategory);
            }
            return  list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成uuid
     * @return
     */
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    /**
     * 生成较段短的uuid
     * @return
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    /**
     * 读取转换连接
     * @param url
     * @return
     */
    public static  Map<String, String> poiReadExcelReadTransform(String url) {
        Map<String, String> map = new HashMap<>();
        File file = new File(url);
        try {
            FileInputStream fileInputStream = FileUtils.openInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastPage = sheet.getLastRowNum();
            for(int i = 1; i < lastPage; i++){
                XSSFRow row = sheet.getRow(i);

                XSSFCell gbCellCell = row.getCell(3);
                gbCellCell.setCellType(CellType.STRING);
                String gbCellValue = gbCellCell.getStringCellValue();


                XSSFCell toTranslateNumCell = row.getCell(4);
                toTranslateNumCell.setCellType(CellType.STRING);
                String toTranslateNumValue = toTranslateNumCell.getStringCellValue();

                map.put(toTranslateNumValue,gbCellValue);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 水设备
     * @param s
     * @return
     */
    public static List<DeviceAddParam> poiReadExcelAndReturnWaterDevice(String s) {

        File file = new File(s);
        try {
            FileInputStream stream = FileUtils.openInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            //获取第一个表单
            XSSFSheet sheet = workbook.getSheetAt(0);
            //获取sheet中最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            List<DeviceAddParam> deviceAddParamList = new ArrayList<>();
            for (int i = 1; i <= lastRowNum; i++) {
                DeviceCommonNameAttrs deviceCommonNameAttrs = new DeviceCommonNameAttrs();
                //获取一条数据
                XSSFRow row = sheet.getRow(i);

                /**
                 *  制造商
                 */
                XSSFCell manufactureCell = row.getCell(0);
                if (null != manufactureCell) {
                    manufactureCell.setCellType(CellType.STRING);
                    String manufactureValue = manufactureCell.getStringCellValue().trim();
                    deviceCommonNameAttrs.setManufacturerName(manufactureValue);
                }

                /**
                 *  deivceCode
                 */
                XSSFCell deviceCodeCell = row.getCell(1);
                if (null != deviceCodeCell) {
                    deviceCodeCell.setCellType(CellType.STRING);
                    String deviceCodeCellValue = deviceCodeCell.getStringCellValue().trim();
                    deviceCommonNameAttrs.setDeviceCode(deviceCodeCellValue);
                }

                deviceCommonNameAttrs.setDeviceTypeName("水");

                //基本属性
                //设备名称
                XSSFCell deviceNameCell = row.getCell(2);
                Map<String, DeviceBasicAttrNameValueVo> basicAttrVoMap = new HashMap<>();
                if (null != deviceNameCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    deviceNameCell.setCellType(CellType.STRING);
                    String deviceName = deviceNameCell.getStringCellValue();
                    //to do fuck pm
                    deviceBasicAttrNameValueVo.setBasicAttrValue(deviceName);
                    basicAttrVoMap.put("deviceName", deviceBasicAttrNameValueVo);
                }

                //检测类型
                String detectTypeValue = null;
                XSSFCell detectTypeCell = row.getCell(3);
                if (null != detectTypeCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    detectTypeCell.setCellType(CellType.STRING);
                     detectTypeValue = detectTypeCell.getStringCellValue();

                }

                String detectionLimitValue = null;
                //检出限
                XSSFCell detectionLimitCell = row.getCell(4);
                if (null != detectionLimitCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    detectionLimitCell.setCellType(CellType.STRING);
                    detectionLimitValue = detectionLimitCell.getStringCellValue();

                }

                String hightLimitCellValue = null;
                //标准上限
                XSSFCell hightLimitCell = row.getCell(5);
                if (null != hightLimitCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    hightLimitCell.setCellType(CellType.STRING);
                    hightLimitCellValue = hightLimitCell.getStringCellValue();
                }

                String lowerLimitCellValue = null;
                //标准下限
                XSSFCell lowerLimitCell = row.getCell(6);
                if (null != lowerLimitCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    lowerLimitCell.setCellType(CellType.STRING);
                    lowerLimitCellValue = hightLimitCell.getStringCellValue();

                }
                String unitCellValue = null;
                        //标准下限
                XSSFCell unitCell = row.getCell(7);
                if (null != unitCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    unitCell.setCellType(CellType.STRING);
                    unitCellValue = unitCell.getStringCellValue();
                }


                String analysisValue = null;
                //分析方法
                XSSFCell analysisCell = row.getCell(8);
                if (null != analysisCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    analysisCell.setCellType(CellType.STRING);
                   analysisValue = analysisCell.getStringCellValue();
                }

                Map<String, DeviceExtendAttrNameValueVo> extendAttrVoMap = new HashMap<>();

                //因子编码
                XSSFCell factorCodeCell = row.getCell(9);
                if (null != factorCodeCell) {
                    DeviceBasicAttrNameValueVo deviceBasicAttrNameValueVo = new DeviceBasicAttrNameValueVo();
                    factorCodeCell.setCellType(CellType.STRING);
                    String factorCodeCellValue = factorCodeCell.getStringCellValue();
                    String[] codeList = factorCodeCellValue.split(",");
                    List<Map<String, DeviceSubAttrNameValueVo>> extendAttrValuesMapList = new ArrayList<>();
                    for (String code : codeList) {
                        Map<String, DeviceSubAttrNameValueVo> subFactorNameMap = new HashMap<>();

                        DeviceSubAttrNameValueVo deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                        deviceSubAttrNameValueVo.setSubAttrValue(code);
                        subFactorNameMap.put("factorCode", deviceSubAttrNameValueVo);

                        deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                        deviceSubAttrNameValueVo.setSubAttrValue(detectTypeValue);
                        subFactorNameMap.put("detectionType", deviceSubAttrNameValueVo);

                        deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                        deviceSubAttrNameValueVo.setSubAttrValue(detectionLimitValue);
                        subFactorNameMap.put("detectionLimit", deviceSubAttrNameValueVo);

                        deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                        deviceSubAttrNameValueVo.setSubAttrValue(hightLimitCellValue);
                        subFactorNameMap.put("highLimit", deviceSubAttrNameValueVo);

                        deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                        deviceSubAttrNameValueVo.setSubAttrValue(lowerLimitCellValue);
                        subFactorNameMap.put("lowerLimit", deviceSubAttrNameValueVo);

                        deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                        deviceSubAttrNameValueVo.setSubAttrValue(unitCellValue);
                        subFactorNameMap.put("unit", deviceSubAttrNameValueVo);

                        deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                        deviceSubAttrNameValueVo.setSubAttrValue(analysisValue);
                        subFactorNameMap.put("analysisMethod", deviceSubAttrNameValueVo);

                        extendAttrValuesMapList.add(subFactorNameMap);
                    }
                    DeviceExtendAttrNameValueVo deviceExtendAttrNameValueVo = new DeviceExtendAttrNameValueVo();
                    deviceExtendAttrNameValueVo.setExtendAttrValuesMapList(extendAttrValuesMapList);
                    extendAttrVoMap.put("factor",deviceExtendAttrNameValueVo);
                }

                //扩展属性
                XSSFCell measureCodeCell = row.getCell(10);
                if (null != measureCodeCell) {
                    measureCodeCell.setCellType(CellType.STRING);
                    String regent  = measureCodeCell.getStringCellValue();
                    String[] split = regent.split("、");
                    DeviceExtendAttrNameValueVo deviceExtendAttrNameValueVo = new DeviceExtendAttrNameValueVo();
                    for (String jjs : split) {
                        List<Map<String, DeviceSubAttrNameValueVo>> extendAttrValuesMapList = new ArrayList<>();
                        Map<String, DeviceSubAttrNameValueVo> subFactorNameMap = new HashMap<>();

                        DeviceSubAttrNameValueVo deviceSubAttrNameValueVo = new DeviceSubAttrNameValueVo();
                        deviceSubAttrNameValueVo.setSubAttrValue(jjs);
                        subFactorNameMap.put("reagentname", deviceSubAttrNameValueVo);
                        extendAttrValuesMapList.add(subFactorNameMap);
                        deviceExtendAttrNameValueVo.setExtendAttrValuesMapList(extendAttrValuesMapList);
                    }
                    extendAttrVoMap.put("reagent",deviceExtendAttrNameValueVo);
                }
                DeviceAddParam deviceAddParam = new DeviceAddParam();
                deviceAddParam.setDeviceCommonNameAttrs(deviceCommonNameAttrs);
                deviceAddParam.setBasicAttrVoMap(basicAttrVoMap);
                deviceAddParam.setExtendAttrVoMap(extendAttrVoMap);
                deviceAddParamList.add(deviceAddParam);
            }
            return deviceAddParamList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
