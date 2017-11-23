package han.playtest;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import han.playmanage.PlayList;
import han.playmanage.PlayListCollection;
import han.playmanage.Song;

public class TestDemo {
	Scanner input = new Scanner(System.in);
	int number;
	PlayList mainPlayList = new PlayList();
	PlayListCollection plc = new PlayListCollection();

	// 测试歌曲类
	public void testSong() {
		// 创建歌曲对象
		Song song1 = new Song("s01", "歌名1", "演唱者1");
		Song song2 = new Song("s02", "歌名2", "演唱者2");
		// 比较歌曲1和歌曲2两个对象是否相等
		System.out.println("song1 == song2 ?" + song1.equals(song2));
		// 显示歌曲信息
		System.out.println(song1 + "\n" + song2);
	}

	// 测试播放列表类
	public void testPlayList() {
		// 创建播放列表对象
		PlayList pl = new PlayList();
		pl.addToPlayList(new Song("s01", "歌名1", "演唱者1"));
		pl.addToPlayList(new Song("s02", "歌名2", "演唱者2"));
		// 显示播放列表中所有歌曲
		pl.displayAllSong();
		// 通过id查询歌曲
		System.out.println(pl.searchSongById("s01") != null ? "该歌曲的信息为：\n" + pl.searchSongById("s01") : "该歌曲在播放列表中不存在！");
		// 通过歌名查询歌曲
		pl.searchSongByName("歌名1");
		// 修改歌曲
		System.out.println("修改歌曲后：");
		pl.updateSong("s01", new Song("s03", "歌曲3", "演唱者3"));
		pl.displayAllSong();
		// 删除歌曲
		System.out.println("删除歌曲后：");
		pl.deleteSong("s02");
		pl.displayAllSong();
	}

	// 测试播放器类
	public void testPlayListCollection() {
		PlayList pl = new PlayList();
		pl.addToPlayList(new Song("s01", "music1", "singer1"));
		pl.addToPlayList(new Song("s02", "music2", "singer2"));
		pl.addToPlayList(new Song("s03", "music3", "singer3"));
		PlayListCollection plc = new PlayListCollection();
		// 添加播放列表
		plc.addPlayList("test", pl);
		plc.addPlayList("test", pl);

		// 通过名字查询播放列表
		plc.searchPlayListByName("list1");
		// 显示所有播放列表名称
		plc.displayPlayListName();
		// 删除播放列表
		plc.deletePlayList(pl);
		plc.displayPlayListName();
		// 导出歌单
		plc.outputPlayList();
	}

	// 主菜单
	public void mainManu() {
		System.out.println("*********************************************");
		System.out.println("               **主菜单**");
		System.out.println("               1--播放列表管理");
		System.out.println("               2--播放器管理");
		System.out.println("               0--退出");
		System.out.println("*********************************************");
	}

	// 播放列表管理菜单
	public void playListMenu() {
		int addMusicNum;
		do {
			System.out.println("*********************************************");
			System.out.println("          **播放列表管理**");
			System.out.println("          1--将歌曲添加到主播放列表");
			System.out.println("          2--将歌曲添加到普通播放列表");
			System.out.println("          3--通过歌曲id查询播放列表中的歌曲");
			System.out.println("          4--通过歌曲名称查询播放列表中的歌曲");
			System.out.println("          5--修改播放列表中的歌曲");
			System.out.println("          6--删除播放列表中的歌曲");
			System.out.println("          7--显示播放列表中的所有歌曲");
			System.out.println("          8--导出歌单");
			System.out.println("          9--返回上一级菜单");
			System.out.println("*********************************************");
			try {
				boolean flag = false;  //判断循环变量
				System.out.println("请输入对应的数字对播放列表进行管理：");  //获得管理选项
				number = input.nextInt();
				switch (number) {
				case 1:
					while (flag == false) {
						flag = true;
						try {
							System.out.println("将歌曲添加到主播放列表\n请输入要添加的歌曲的数量：");
							addMusicNum = input.nextInt();
							for (int i = 0; i < addMusicNum; i++) {  //循环获得添加歌曲的信息
								System.out.println("请输入第" + (i + 1) + "首歌曲：\n请输入歌曲的id：");
								String addMusicId = input.next();
								if (mainPlayList.searchSongById(addMusicId) != null) {  //判断歌曲是否在列表已存在
									System.out.println("歌曲已存在！");  //提示歌曲存在
									i--;continue;  //继续下一次循环
								}
								System.out.println("请输入歌曲的名称:");   //获得歌名名称
								String addMusicName = input.next();
								System.out.println("请输入演唱者：");  //获得演唱者信息
								String addMusicSinger = input.next();
								Song addSong = new Song(addMusicId, addMusicName, addMusicSinger);   //用获得的信息创建歌曲对象
								mainPlayList.addToPlayList(addSong);  //添加歌曲对象到主播放列表
							}
						} catch (InputMismatchException e) {  //捕获输入异常
							System.out.println("输入格式错误，请重新输入！");
							input.next();
							flag = false;continue;
						}
					}
					if (flag)
						plc.getPlayListMap().put("主播放列表", mainPlayList);  //判断条件为true，添加列表名和列表到Map集合
					break;
				case 2:
					System.out.println("将歌曲添加到普通播放列表");  
					System.out.println("请输入要添加的播放列表名称：");   //获得列表名称
					String normalListName = input.next();  
					if(plc.getPlayListMap().containsKey(normalListName))
						plc.addPlayList(normalListName, mainPlayList);  //列表已存在，继续添加歌曲
					else
						System.out.println("该播放列表不存在，请先将播放列表添加到播放器中！");  //列表不存在，给出提示信息
					break;
				case 3:
					System.out.println("通过歌曲id查询播放列表中的歌曲\n请输入要查询的播放列表名称：");   //获得查询的播放列表名
					String searchListId = input.next();
					if (plc.getPlayListMap().containsKey(searchListId)) {   //如果列表已存在
						System.out.println("请输入要查询的歌曲id：");  //获得查询id
						String searchMusicId = input.next();
						if ((plc.getPlayListMap().get(searchListId).searchSongById(searchMusicId) != null)) {  //若歌曲存在，则显示歌曲信息
							System.out.println("该歌曲的信息为" + plc.getPlayListMap().get(searchListId).searchSongById(searchMusicId));  
						} else
							System.out.println("该歌曲在" + searchListId + "播放列表中不存在！");  //提示歌曲不存在
					} else
						System.out.println(searchListId+"播放列表不存在！ ");  //提示列表不存在
					break;
				case 4:
					System.out.println("通过歌曲名称查询播放列表中的歌曲\n请输入要查询的播放列表名称：");  //获得查询列表名
					String searchListName = input.next();
					if (plc.getPlayListMap().containsKey(searchListName)) {  //判断列表是否存在
						System.out.println("请输入要查询的歌曲名称：");  //获得歌名
						String searchMusicName = input.next();
						plc.getPlayListMap().get(searchListName).searchSongByName(searchMusicName);  //查询歌曲
					} else 
						System.out.println(searchListName + "播放列表中不存在！");  //提示列表不存在信息
					break;
				case 5:
					System.out.println("修改播放列表中的歌曲\n请输入要修改的歌曲id：");  //获得歌曲id
					String reviseMusicId = input.next();
					if (mainPlayList.searchSongById(reviseMusicId) != null)   //若歌曲存在，修改歌曲
						mainPlayList.updateSong(reviseMusicId, mainPlayList.searchSongById(reviseMusicId));
					else
						System.out.println("该歌曲不存在！");  //提示歌曲不存在信息
					break;
				case 6:
					System.out.println("删除播放列表中的歌曲\n请输入要删除的歌曲id：");   //获得歌曲id
					String deleteMusicId = input.next();
					if (mainPlayList.searchSongById(deleteMusicId) != null) {   // 若歌曲存在，同时删除主列表和普通列表的歌曲
						Iterator<PlayList> it = plc.getPlayListMap().values().iterator();
						while(it.hasNext())
							it.next().deleteSong(deleteMusicId);
						System.out.println("删除成功！");  //提示删除成功
					} else 
						System.out.println("歌曲不存在！");  //提示歌曲不存在
					
					break;
				case 7:
					System.out.println("显示播放列表中的所有歌曲\n请输入要显示的播放列表名称：");   //获得播放列表名
					String displayListName = input.next(); 
					if (displayListName.equals("主播放列表"))   //显示主播放列表
						mainPlayList.displayAllSong();
					else if (plc.getPlayListMap().containsKey(displayListName))   //显示普通列表
						plc.getPlayListMap().get(displayListName).displayAllSong();
					else
						System.out.println("播放列表不存在！");  //若列表不存在，给出提示
					break;
				case 8:
					System.out.println("导出歌单");  //导出歌单
					plc.outputPlayList();
					break;
				case 9:break;  //结束循环，返回上一级菜单
				default:
					System.out.println("输入信息不正确，请重新输入！");  //提示输入错误信息
				}
			} catch (InputMismatchException e) {
				System.out.println("输入格式不正确，请重新输入！");   //捕获输入异常
				input.next();
				continue;
			}
		} while (number != 9);  //循环条件
	}

	// 播放器菜单
	public void playerMenu() {
		do {
			try {
				System.out.println("*********************************************");
				System.out.println("          **播放器管理**");
				System.out.println("          1--向播放器添加播放列表");
				System.out.println("          2--从播放器删除播放列表");
				System.out.println("          3--通过名字查询播放列表信息");
				System.out.println("          4--显示所有播放列表名称");
				System.out.println("          9--返回上一级菜单");
				System.out.println("*********************************************");
				System.out.println("请输入对应的数字对播放器进行管理：");
				number = input.nextInt();
				switch (number) {
				case 1:
					System.out.println("向播放器添加播放列表");  //显示选项1内容
					System.out.println("请输入要添加的播放列表名称：");  //从键盘获得要添加列表名称
					String addListName = input.next();  
					if (plc.getPlayListMap().containsKey(addListName))   //如果要添加列表已存在，则提示信息
						System.out.println("播放列表已存在！");
					else {
						PlayList addPlayList = new PlayList();  //若不存在，创建播放列表对象
						plc.getPlayListMap().put(addListName, addPlayList);  //添加新的列表名和列表到Map集合
						plc.addPlayList(addListName, mainPlayList);  //向新列表中添加歌曲
					}
					break;
				case 2:
					System.out.println("从播放器删除播放列表");  //显示选项2内容
					plc.deletePlayList(mainPlayList);break;  //删除列表
				case 3:
					System.out.println("通过名字查询播放列表信息\n请输入要查询的播放列表名称：");  //显示选项3内容、并从键盘获得要查询播放列表名称信息
					String searchListName = input.next();
					plc.searchPlayListByName(searchListName);break;  //查询列表
				case 4:
					System.out.println("显示所有播放列表名称\n播放列表名称为：");  //显示选项4内容，并从键盘获得播放列表名称
					plc.displayPlayListName();break;  //显示列表
				case 9:break;  //结束循环，返回上一级
				default:
					System.out.println("输入信息不正确，请重新输入！");  //提示default信息
				}
			} catch (InputMismatchException e) {
				System.out.println("输入格式不正确，请重新输入！");  //捕获输入异常，并继续下一次循环
				input.next();  //从键盘获得异常内容，避免无限循环
				continue; 
			}
		} while (number != 9);  //循环条件

	}

	// 主流程实现
	public void test() {
		do {
			mainManu();  //显示主菜单
			try {
				System.out.println("请输入对应数字进行操作：");   //获得选择信息
				number = input.nextInt();
				switch (number) {
				case 1:
					playListMenu();  //播放列表管理
					break;
				case 2:
					playerMenu();  //播放器管理
					break;
				case 0:
					System.out.println("退出管理！");   //退出管理
					System.exit(-1);
				default:
					System.out.println("输入信息不正确，请重新输入！");  //提示错误信息
				}
			} catch (InputMismatchException e) {
				System.out.println("输入格式不正确，请重新输入！");  //捕获输入异常
				input.next();
				continue;
			}
		} while (number != 0);  //循环条件
	}

	public static void main(String[] args) {
		TestDemo td = new TestDemo();
		// td.testSong(); //测试歌曲类
		// td.testPlayList(); //测试播放列表类
		// td.testPlayListCollection(); //测试播放器类
		td.test();  //主流程测试
	} 

}
