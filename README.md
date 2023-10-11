# mj_fileSDK
文件浏览浏览器demo，使用明哲文件SDK，免费提供安全、高性能、高兼容性、低内存占用的文件浏览能力，支持 word、excel、ppt、pdf、txt 及 dwg、chm 等各类文档格式。

Demo-DownloadUrl
https://www.xcxwo.com/file_reader

## 明哲文件SDK优点
1. 安全： 本地打开，SDK无任何网络请求以及权限需要。
2. 高兼容性：乱码、文本显示以及排版优化，对office的3000+属性支持度高。
3. 性能：打开速度在600ms以下，各类文件成功率99.93%。
4. 内存：
   （1） 按需解析，动态加载。
   （2） 根据图片特性，采取采样上屏，Tile绘制，绘制缓存等策略。

## demo图片：
   <img width="360" height="640" src=https://github.com/vp-noone/mingzhe_file_SDK/assets/37281667/f90c4abb-7b46-4388-b313-24e47d88eb83)>
   
   <img width="360" height="640" src=https://github.com/vp-noone/mingzhe_file_SDK/assets/37281667/ad06c8c6-49f9-4b24-b97d-27c703db800f)>
   
   <img width="360" height="640" src=(https://github.com/vp-noone/mingzhe_file_SDK/assets/37281667/d49165a5-f52e-48a2-b0cc-6947596f55f0)>
## 使用方法
   SDK 初始化接口：
   ```
      //同步初始化：
      public static boolean initSDK(Context context)

      //异步初始化：
      public static void initSDKAsync(Context context)

      //是否初始化完成：
      public static boolean isInitSuccess()
   ```
   文件打开接口：
   以Dialog形式本地打开文件
   ```
     /**
     * file：为要打开的本地文件，文件名要带有类型后缀，如：txt、pdf等，否则，需要调用public static void openFileDialog(Context context, File file, String ext, IFileCallback callback)
     * callback：接收调用回调，
     */
     public static void openFileDialog(Context context, File file, IFileCallback callback)
   ```

   以Layout形式打开本地文件
      
   ```
     /**
     * frameLayout，文件将展示在frameLayout中。
     */
     public static void openFile(Context context, File file, FrameLayout frameLayout, IFileCallback callback)
   ```

功能接口：

      跳转页面
      public static boolean TurnToPage(int page)

      设置PPT为翻页模式（默认滑动模式）
      public static boolean enablePageMode()

      允许文件拷贝长按菜单
      public static boolean enableCopyMenu()


