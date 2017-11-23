package han.playmanage;
/**
 * 歌曲类
 * 属性：歌曲id、歌曲名、演唱者。
 * 方法：构造方法、getter和setter、判断歌曲是否重复、描述歌曲信息方法。
 */
public class Song {
	//成员属性：歌曲id、歌曲名name、演唱者singer
	private String id, name, singer;
	//构造方法
	public Song(){
	}
	public Song(String id, String name, String singer){
		this.setId(id);
		this.setName(name);
		this.setSinger(singer);
	}
	//getter,setter方法
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	//hashCode方法
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((singer == null) ? 0 : singer.hashCode());
		return result;
	}
	//equals方法
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		else if(obj.getClass() == Song.class){
			Song song = (Song)obj;
			return (this.getId().equals(song.getId()) && this.getName().equals(song.getName()) && 
					this.getSinger().equals(song.getSinger()));
		}
		else
			return false;
	}
	//toString方法
	@Override
	public String toString() {
		return "歌曲信息：id为"+this.getId()+"，歌曲名称为："+this.getName()+"，演唱者为："+this.getSinger();
	}
	
}
