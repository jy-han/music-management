package han.playmanage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * 播放器类 属性：播放列表集合。 方法：添加播放列表、删除播放列表、通过名字查询播放列表、显示所有播放列表。
 */
public class PlayListCollection implements Serializable {
	Scanner input = new Scanner(System.in);

	// 成员属性：存放播放列表的集合
	private Map<String, PlayList> playListMap;

	// 构造方法
	public PlayListCollection() {

	}

	public PlayListCollection(Map<String, PlayList> playListMap) {
		this.setPlayListMap(playListMap);
	}

	// getter, setter
	public Map<String, PlayList> getPlayListMap() {
		if (this.playListMap == null) // 如果播放器类对象还没创建，则创建新的对象
			this.playListMap = new HashMap<>();
		return playListMap;
	}

	public void setPlayListMap(Map<String, PlayList> playListMap) {
		this.playListMap = playListMap;
	}

	// 添加播放列表
	public void addPlayList(String listName, PlayList playList) {
		String answer;
		boolean flag = true, check = true;
		
		do {
			check = true;
			try {
				System.out.println("请输入要添加的歌曲的数量："); // 从键盘获得要添加的歌曲数量
				int addNum = input.nextInt();
				for (int i = 0; i < addNum; i++) { // 循环获得歌曲的信息
					System.out.println("请输入第" + (i + 1) + "首歌曲：\n请输入歌曲id："); // 获得歌曲id信息
					String addId = input.next();
					if (playList.getMusicList().contains(playList.searchSongById(addId)) == false) { // 从id判断该歌曲是否已经在主播放列表存在
						System.out.println("该歌曲在主播放列表不存在，继续输入歌曲的其他信息！\n请输入歌曲名称："); // 如果不在主播放列表，则继续获得歌曲名称、演唱者数据
						String addMusicName = input.next();
						System.out.println("请输入演唱者："); // 获得演唱者信息
						String addSinger = input.next();
						playList.addToPlayList(new Song(addId, addMusicName, addSinger)); // 将歌曲信息存入主播放列表
						this.getPlayListMap().get(listName).addToPlayList(playList.searchSongById(addId)); // 将歌曲信息同时存入新建的列表
					} else {
						do {
							System.out.println("该歌曲在主播放列表已存在，是否添加到" + listName + "播放列表？ Y?N"); // 如果从id判断歌曲已经在主播放列表存在，继续判断是否要存入新建列表
							answer = input.next();
							flag = true;

							if (answer.equals("Y") || answer.equals("y")) { // 如果获得肯定信息，则将歌曲存入新建列表
								this.getPlayListMap().get(listName).addToPlayList(playList.searchSongById(addId));
							} else if (answer.equals("N") || answer.equals("n")) { // 如果获得否定信息，则重新获得要添加的歌曲信息，继续下一次循环
								System.out.println("请重新输入要添加的歌曲信息！");
								i--;
								break;
							} else {
								System.out.println("输入错误！"); // 如果获得Y和N以外的信息，则提示错误
								flag = false;
							}
						} while (flag == false);
						continue;
					}
				}
			} catch (InputMismatchException e) { // 捕获输入信息的异常
				System.out.println("输入格式不正确！");
				check = false;
				input.next();
				continue;
			}
		} while (check == false);

		System.out.println("主播放列表：");
		playList.displayAllSong();
		System.out.println("普通播放列表：");
		this.getPlayListMap().get(listName).displayAllSong();
	}

	// 删除播放列表
	public void deletePlayList(PlayList playList) {
		System.out.println("请输入要删除的播放列表名称："); // 从键盘获得要删除播放列表的名称
		String deleteName = input.next();
		if (this.getPlayListMap().containsKey(deleteName)) { // 判断该列表是否存在
			this.getPlayListMap().remove(deleteName); // 若存在，则删除列表信息
			System.out.println("播放列表已删除！");
		} else {
			System.out.println("没有找到播放列表信息！"); // 若不存在，则显示提示信息
		}
	}

	// 通过名字查询播放列表
	public PlayList searchPlayListByName(String name) {
		if (this.getPlayListMap().containsKey(name)) { // 判断该名称对应的播放列表是否存在，若存在返回该列表名称信息
			System.out.println("该播放列表存在！\n该播放列表的名称为：" + name);
			this.getPlayListMap().get(name).displayAllSong();
		}
		return this.getPlayListMap().get(name);
	}

	// 显示所有播放列表名称
	public void displayPlayListName() {
		Iterator<String> it = this.getPlayListMap().keySet().iterator(); // 使用迭代器显示所有播放列表的名称
		while (it.hasNext())
			System.out.println(it.next());
	}

	// 导出歌单
	public void outputPlayList() {
		System.out.println("请输入要导出的播放列表名称："); // 从键盘获得要导出歌单对应的播放列表的名称
		String outputList = input.next() + "的歌单.txt"; // 创建导出歌单文件的名称
		try {
			File file = new File(outputList); // 创建文件对象
			file.createNewFile(); // 创建要导出歌单对应的播放列表名称的文件
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)); // 创建对象输出流对象
			oos.writeObject((this.getPlayListMap().get(outputList))); // 输出对象信息到文件
			oos.flush(); // 清空输出流
			oos.close(); // 关闭输出流
			System.out.println("歌单导出成功！"); // 显示导出成功信息
		} catch (FileNotFoundException e) { // 捕获文件未找到异常
			e.printStackTrace();
		} catch (IOException e) { // 捕获输入输出异常
			e.printStackTrace();
		}
	}
}
