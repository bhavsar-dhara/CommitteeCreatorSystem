package main.java.classes;

public class Publication {
	private Long id;
	private String title;
	private String type;
	private int pbyear;
	private String pages;
	private String journal;
	private String ee;
	private String url;
	private String volume;
	private String number;
	private String booktitle;
	private String isbn;
	private String publisher;
	private String editor;
	private String school;
	
	public Publication() {
		super();
	}

	public Publication(String title, String type, int pbyear, String journal, String ee, String url,
			String volume, String number, String booktitle, String isbn, String publisher, String editor,
			String school) {
		super();
		this.title = title;
		this.type = type;
		this.pbyear = pbyear;
		this.journal = journal;
		this.ee = ee;
		this.url = url;
		this.volume = volume;
		this.number = number;
		this.booktitle = booktitle;
		this.isbn = isbn;
		this.publisher = publisher;
		this.editor = editor;
		this.school = school;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPbyear() {
		return pbyear;
	}

	public void setPbyear(int pbyear) {
		this.pbyear = pbyear;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getEe() {
		return ee;
	}

	public void setEe(String ee) {
		this.ee = ee;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBooktitle() {
		return booktitle;
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	@Override
	public String toString() {
		
		return super.toString();
	}
}
