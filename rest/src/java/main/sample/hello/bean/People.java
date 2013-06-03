package sample.hello.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class People {

	private String name;
	private byte[] image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
