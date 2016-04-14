# ByterealBLEDeviceSDKDemo
Bytereal Android版SDK
一、 简介
Bytereal Scan Android SDK是佰睿官方为Android平台推出的基于BLE设备扫描的集成环境,（以下简称SDK）。
1.Bytereal-scan-sdk-v1.2.0.aar  SDK 集成包
2.ByterealDeviceScanSDK.doc：此文件包含SDK说明。
二、 阅读对象
本文档面向所有使用该SDK的开发人员、测试人员、合作伙伴以及对此感兴趣的其他用户。
三、 运行环境
开发环境
Windows 10 X64
JDK7
Android Studio 1.5.1/2.0
运行环境
Android 4.3以上
四、 开发指南
1. 添加aar包
将Bytereal-scan-sdk-v1.2.0.aar包集成到项目中
2.添加所需权限
<!-- 使用蓝牙权限 -->
<uses-permission
	android:name="android.permission.BLUETOOTH"
/>
<!-- 蓝牙管理权限 -->
<uses-permission 	android:name="android.permission.BLUETOOTH_ADMIN"
/>
3. 实现回调接口
public class MainActivity extends Activity implements NewIBeaconCallback{
.....
}
4.实现回调方法
@Override
public void endOfTheScan() {
// 当每次扫描结束后,回调当前方法
// 被认定为暂未丢失信号的BLE设备↓可以从这个对象中获取
IBeaconScanConfig.IBeaconListCache;
}

@Override
public void findNewIBeacon(IBeacon newIBeacon) {
// 当发现新设备后,回调当前方法,每个新设备只回调一次
// 请根据实际需求,以通知的形式通知用户,注意通知间隔,注意信号丢失问题
// 这里建议以通知队列的形式存放通知,间隔一定时间再通知用户,避免连续.
// 针对上述情况,请注意在通知前判断设备信号是否已经丢失.
// 此处还请使用Handler通知UI线程更新.否则将无效.
}
5.实例化扫描参数
// 设置回调接口
IBeaconScanConfig.newIBeaconCallback = this;
// 设置信号丢失时长,超过该时长没有再次发现设备,判定为信号丢失
// 默认为15秒
// 建议(扫描+间隔)*3.即,连续三次没有发现设备信号才认为丢失
IBeaconScanConfig.scanCacheDurationTime = 15000;
// 扫描时长,默认为3秒.请根据实际需求和电量消耗考虑
IBeaconScanConfig.scanDurationTime = 3000;
// 扫描间隔,默认为2秒.请根据实际需求和电量消耗考虑
IBeaconScanConfig.scanSpacingTime = 2000;
// 请在这里注册蓝牙状态监听广播,请注意顺序,一定先开启广播后开启蓝牙
// 请在这里开启蓝牙,然后SDK会开启BLE扫描服务
6.注册/注销蓝牙状态监听广播
// 注册并开启蓝牙状态监听广播
RegisteredBroadcast.registeredBluetoothBroadcast(this); 
// 注销蓝牙状态广播监听方法如下:
RegisteredBroadcast.unRegisterBluetoothBroadcast(this);
7.主动关闭后台蓝牙BLE扫描服务
// 默认情况下,当蓝牙不可用时,将关闭后台扫描服务
// 建议:为了省电,判断扫描结果连续为空的次数,当达到指定次数,关闭蓝牙,SDK将自动关闭后台扫描服务,间隔一定时间后,再开启蓝牙即可,广播会自动开启后台蓝牙扫描服务.
BluetoothBroadcastReceiver.unBindService();
五、 API描述
1. 开启BLE后台扫描
SDK中已经封装好广播,无需主动开启扫描.广播会监听蓝牙状态,当蓝牙可用时,会主动开启后台BLE扫描,并对BLE进行解码,封装成IBeacon对象返回.
详情请参阅:第四节第6点,第7点;
2.关闭BLE后台扫描
SDK中已经封装好广播,当蓝牙关闭后,广播SDK会自动关闭后台蓝牙扫描.
若想灵活控制,可以通过广播+手动关闭服务+蓝牙开关的形式进行控制.任意2项即可灵活控制服务.根据方便程度,建议广播+蓝牙开关的形式.必要时,可手动关闭.
六、 简单示例
导入项目,运行后开启蓝牙查看日志,并仔细阅读注释代码.
七、 联系我们
感谢您的阅读,如果您在开发中遇到了问题，请通过以下方式联系我们。 邮箱: support@bytereal.com  电话 400-8899-181

