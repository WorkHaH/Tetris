# 1、问题描述：    
俄罗斯方块屏幕中央有一个矩形容器，程序刚开始时是空的；当鼠标单击“开始”菜单时，矩形容器内从上向下随机出现俄罗斯方块的部件。通过键盘上的左右键分别左右移动部件（一个单位），向上键顺时针旋转90度。当部件到达容器底部或已停止的部件上时，停止；当容器的同一行被部件填满时，该行消失。其他行依次向下移动。

# 2、设计要求：
要求设计程序输出如下：
1. 屏幕中央有一个矩形容器，选择“开始”菜单，俄罗斯方块的部件随机产生并在容器中从上向下下落。
2. 有七种标准俄罗斯方块部件，并随着键盘上的左右键分别左右移动，随着键盘上的向上键分别顺时针旋转90度。
3. 当部件到达容器底部或已停止的部件上时，停止；当同一行部件完整拼接上时，该行消失，其他行向下移动，在适 当位置显示当前累计分。
4. 当部件总行数超过矩形容器高度时，提示“游戏失败”信息并停止。


# 3、问题分析：
1. **七种标准俄罗斯方块部件的建立和存储：**
每一种俄罗斯方块部件都进行拆分成一个个方块，选定一个方块作为<font color=red>中心点</font>，该方块的存储坐标为（0，0），其余的方块以中心点为标准存储坐标，把这个部件的各个方块的坐标存储到一个自定义类<font color=red>Blocks</font>中<font color=red>（Blocks类把各个坐标存储到Point对象中）</font>，再把所有7种部件的Blocks对象存储到Blocks数组中备用。
2. **俄罗斯方块部件的控制：**
首先用<font color=red>java.awt</font>包下的<font color=red>画笔Graphics</font>根据该俄罗斯方块部件的各个方块的Point对象画在面板上；对于部件的左右向下移动，设置鼠标监听器和键盘监听器，监听获取<font color=red>偏移量</font>，使部件整体移动，在重新画在面板上，而对于部件的自动掉落用无限循环的线程执行不断画即可；对于部件的旋转，根据三角函数可以得出一个公式，部件经过90°旋转，<font color=red>**坐标x标变为原来y坐标的负值（即-y），坐标y变为原来x坐标的x值**</font>，因此，旋转只需要根据公式改变该部件各个方块的坐标，再重新画在面板上即可。当然还要注意边界问题，旋转之前先检测是否越界，才决定能否旋转，不止是边界还有相邻的方块不能重叠，所有旋转前还需要检测是否和其他部件重叠再决定是否旋转。
3. **俄罗斯方块的计分和结束：**
保存以前的俄罗斯方块部件采用的<font color=red>二维数组</font>存储。在面板对应的位置有部件存在则在二维数组对应的位置的值设为对应部件的编号，反之为0，每重画一次面板都会先访问这个二维数组再根据<font color=red>偏移量</font>，画出这一次之前的所有方块部件和画这次的方块部件；主要一行都有方块则得分，<font color=red>即二维数组中只要有一（多）行都是非0值就得分并消除这一行（也可以多行），消除的方法是把数组的值整体移动到下一（多）行，再重画</font>；结束就是二维数组只要有一列都是非0值则**游戏结束**，此时的得分就是你最终的分数。
4. **俄罗斯方块的登录和荣誉榜**
本项目运用到了<font color=red>轻量级数据库splite</font>，玩家可以注册登录用户，不登陆就开始游戏的玩家默认为未知玩家；登录页面首先会检测当前的昵称是否有存在，要是存在当前昵称，则输入密码登录，反之注册；玩家进行游戏直到游戏结束的最终得分会根据你的当前用户然后把存到数据库中，然后用画笔画在荣誉榜上；荣誉榜会取数据库中的分数**前四名**进行展示；<font color=red>为了不出现多用户的冲突需要用到synchronized同步。</font>

5. **俄罗斯方块的下一个展示和游戏暂停与继续**
游戏的下一个展示就是把下一个俄罗斯方块部件准备好再画再提示区；游戏的暂停就是让<font color=red>线程不做任何操作</font>，继续就是恢复该项目的“游戏中”的状态。
# 4、代码演示
1、数据库player的players表<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/2d42347e083146dc9324ad6df903775e.png)

2、俄罗斯方块的部件可以用鼠标控制也可以用键盘控制
鼠标控制：开始按钮上的四个按键
键盘控制：左键右键控制左右、下键控制向下、上键控制旋转<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/f32215b01ad94333aa24d3e8b4d469bf.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_10,color_FFFFFF,t_70,g_se,x_16)

3、点击登录按钮<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/24d89ebb2d34417598d5c2153572cc3a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_10,color_FFFFFF,t_70,g_se,x_16)

4、登录界面<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/39d8e1f4484849ed983ba430489664ca.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_10,color_FFFFFF,t_70,g_se,x_16)

5、注册玩家1<br><br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/eafec010398f437a8c8eeb9d31609b73.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_10,color_FFFFFF,t_70,g_se,x_16)

6、表players数据<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/f3f00dfcc6b6414fa0db8418d6d9d460.png)

7、结束游戏，玩家1分数为0<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/a099bac19f2a40158767507ccc2b2c00.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_11,color_FFFFFF,t_70,g_se,x_16)

8、荣誉榜取表分数前四名玩家信息<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/356b43c03ee84ec383a75b7d4d7142c6.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_11,color_FFFFFF,t_70,g_se,x_16)

9、点击登录按钮<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/97116d20de614157932f5673323c2c77.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_11,color_FFFFFF,t_70,g_se,x_16)

10、密码错误<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/62979e1931464399afcbf60ed5e1c264.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_12,color_FFFFFF,t_70,g_se,x_16)

11、重新开始游戏<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/e67741c08ec34f79a6b4154fdc7111e1.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_12,color_FFFFFF,t_70,g_se,x_16)

12、暂停后可继续<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/c0a9420352214b93a2d542c134708360.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_12,color_FFFFFF,t_70,g_se,x_16)

13、消除一行，现在分数为10<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/fa4e854f778a42b0818bd2351ca1ddd5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_12,color_FFFFFF,t_70,g_se,x_16)

14、游戏结束，玩家1分数为10<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/7510386662df435485d894ad90490bca.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_13,color_FFFFFF,t_70,g_se,x_16)

15、荣誉榜刷新<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/649e02968c4a4120a4d42e31eee7b000.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_12,color_FFFFFF,t_70,g_se,x_16)

16、注册玩家2<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/6ad6169214834bb18537b68d4486c125.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_13,color_FFFFFF,t_70,g_se,x_16)

17、<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/096e602da87340789ddd27756ec44ad3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_13,color_FFFFFF,t_70,g_se,x_16)

18、游戏结束，玩家2分数为20<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/645586d5b89e42c886d24ff7fdeebb7e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_13,color_FFFFFF,t_70,g_se,x_16)

19、数据库更新<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/cc499e87438f40b99d773c993bb11c0b.png)

20、荣誉榜更新<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/e7b2625c46154e97840ecd0fe3e2359d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAV29ya0hhSA==,size_13,color_FFFFFF,t_70,g_se,x_16)
