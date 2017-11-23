package han.playmanage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
/**
 * 播放列表类
 * 属性：播放列表名称、歌曲集合。
 * 方法：添加显示歌曲、查询歌曲、修改及删除歌曲的信息。
 */
public class PlayList {
	Scanner input = new Scanner(System.in);
	//成员属性：播放列表名称、歌曲集合
	private String playListName;
	private List<Song> musicList;
	
	//构造方法
	public PlayList(){
		
	}
	public PlayList(String playListName, List<Song> musicList){
		this.setPlayListName(playListName);
		this.setMusicList(musicList);
	}
	//getter,setter
	public String getPlayListName() {
		return playListName;
	}
	public void setPlayListName(String playListName) {
		this.playListName = playListName;
	}
	public List<Song> getMusicList() {
		if(this.musicList == null)
			this.musicList = new ArrayList<Song>();
		return this.musicList;
	}
	public void setMusicList(List<Song> musicList) {
		this.musicList = musicList;
	}
	
	//将歌曲添加到播放列表
	public void addToPlayList(Song song){
		if (this.getMusicList().contains(song))  //判断歌曲是否已经存在
			System.out.println("歌曲已存在！");
		else
			this.getMusicList().add(song);	//若不存在，加入歌单		 
	} 
	
	//显示播放列表中所有歌曲
	public void displayAllSong(){
		System.out.println("播放列表中所有歌曲信息为：");
		for(Song song : this.getMusicList())  //遍历歌曲列表输出歌曲信息
			System.out.println(song);
	}
	
	//通过id查询歌曲
	public Song searchSongById(String id){
		Song temp, song = null;
		Iterator<Song> it = this.getMusicList().iterator();  //使用迭代器查询歌曲信息
		while(it.hasNext()) {
			temp = (Song)it.next();
			if(temp.getId().equals(id)){
				song = temp;
			}
		}
		return song;
	}
	
	//通过名称查询歌曲
	public Song searchSongByName(String name){
		boolean flag = false;
		Song temp;
		Iterator<Song> it = this.getMusicList().iterator();
		while(it.hasNext()) {
			temp = (Song)it.next();
			if(temp.getName().equals(name)){
				flag = true;
				System.out.println("该歌曲的信息为：\n"+temp);
			}
		}
		if(flag == false)
			System.out.println("该歌曲在播放列表中不存在！");
		return null;
	}
	
	//修改歌曲
	public void updateSong(String id, Song song){
		System.out.println("请输入修改后的歌曲名称："); //从键盘获得修改后歌曲名称信息
		String revisedMusicName = input.next();
		System.out.println("请输入修改后的演唱者："); //从键盘获得修改后歌曲演唱者信息
		String revisedSinger = input.next();
		song.setName(revisedMusicName);  //用setName方法设置新的歌曲名称
		song.setSinger(revisedSinger);  //用setSinger方法设置新的演唱者信息
		if(this.getMusicList().contains(id)) {  //若歌曲列表中包含要修改歌曲的id，则删除该歌曲，并添加信息修改后的歌曲
			this.getMusicList().remove(searchSongById(id));
			this.getMusicList().add(song);
		}
		System.out.println("修改成功！");
	}
	
	//从播放列表删除歌曲
	public void deleteSong(String id){
		if(this.getMusicList().contains(searchSongById(id)))  //若歌曲列表中包含要修改歌曲的id，则删除该歌曲	
			this.getMusicList().remove(searchSongById(id));
		
	}
}
