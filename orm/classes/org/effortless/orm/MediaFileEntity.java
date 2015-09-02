package org.effortless.orm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

import javax.activation.MimetypesFileTypeMap;
//import javax.persistence.Entity;
//import javax.persistence.Lob;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Table;


import org.effortless.core.FileUtils;
import org.effortless.core.FilenameUtils;
import org.effortless.core.Hex;
import org.effortless.core.IOUtils;
import org.effortless.core.MetadataFiles;
import org.effortless.core.ObjectUtils;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.impl.ColumnExtraType;
import org.effortless.orm.impl.EntityFilter;
import org.effortless.orm.impl.PropertiesLoader;
import org.effortless.orm.util.FileHashes;
//import org.hibernate.annotations.Tuplizer;
//import org.hibernate.tuple.entity.PojoEntityTuplizer;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;


//http://code.google.com/p/xxhash/ Para calcular huellas
//https://github.com/jpountz/lz4-java Para comprimir con rapidez  (http://fastcompression.blogspot.com.es/p/lz4.html)
public class MediaFileEntity extends FileEntity {

	public MediaFileEntity () {
		super();
	}
	
	protected void initiate () {
		super.initiate();

		initiateHash1();
		initiateHash2();
		initiateHash3();
		initiateRegisterDate();
		initiateLastModification();
		initiateTitle();
		initiateSubject();
		initiateAuthor();
		initiateKeywords();
		initiateCompress();
		initiateWidth();
		initiateHeight();
		initiateHasVideo();
		initiateHasAudio();
		initiateCanSeekOnTime();
		initiateBitsPerSample();
		initiateTotalDataRate();
		initiateVideoDataRate();
		initiateAudioDataRate();
		initiateDuration();
		initiateFrameRate();
		initiateByteLength();
		initiateFileVersion();
		initiateSampleRate();
		initiateChannels();
		initiateAudioChannelType();
		initiateAudioCompressor();
		initiateLanguage();
		initiateSoftware();
		initiateImageCount();
		initiateParagraphCount();
		initiatePageCount();
		initiateObjectCount();
		initiateCharacterCount();
		initiateTableCount();
		initiateWordCount();
		initiateRevisionNumber();
		initiateEditTimeSeconds();
		initiatePrintDate();
		initiateLastModified();
		initiateCreationDate();
	}

	protected String hash1;

	protected void initiateHash1() {
		this.hash1 = null;
	}

	public String getHash1() {
		_loadOnDemand("hash1", this.hash1, __DEFINITION__);
		return this.hash1;
	}

	public void setHash1(String newValue) {
		_loadOnDemand("hash1", this.hash1, __DEFINITION__);
		_setProperty("hash1", this.hash1, this.hash1 = newValue);
	}

	protected String hash2;

	protected void initiateHash2() {
		this.hash2 = null;
	}

	public String getHash2() {
		_loadOnDemand("hash2", this.hash2, __DEFINITION__);
		return this.hash2;
	}

	public void setHash2(String newValue) {
		_loadOnDemand("hash2", this.hash2, __DEFINITION__);
		_setProperty("hash2", this.hash2, this.hash2 = newValue);
	}
	
	protected String hash3;

	protected void initiateHash3() {
		this.hash3 = null;
	}

	public String getHash3() {
		_loadOnDemand("hash3", this.hash3, __DEFINITION__);
		return this.hash3;
	}

	public void setHash3(String newValue) {
		_loadOnDemand("hash3", this.hash3, __DEFINITION__);
		_setProperty("hash3", this.hash3, this.hash3 = newValue);
	}
	
	protected Date registerDate;

	protected void initiateRegisterDate() {
		this.registerDate = null;
	}

	public Date getRegisterDate() {
		_loadOnDemand("registerDate", this.registerDate, __DEFINITION__);
		return this.registerDate;
	}

	public void setRegisterDate(Date newValue) {
		_loadOnDemand("registerDate", this.registerDate, __DEFINITION__);
		_setProperty("registerDate", this.registerDate, this.registerDate = newValue);
	}
	
	protected Date lastModification;

	protected void initiateLastModification() {
		this.lastModification = null;
	}

	public Date getLastModification() {
		_loadOnDemand("lastModification", this.lastModification, __DEFINITION__);
		return this.lastModification;
	}

	public void setLastModification(Date newValue) {
		_loadOnDemand("lastModification", this.lastModification, __DEFINITION__);
		_setProperty("lastModification", this.lastModification, this.lastModification = newValue);
	}

	protected String title;

	protected void initiateTitle() {
		this.title = null;
	}

	public String getTitle() {
		_loadOnDemand("title", this.title, __DEFINITION__);
		return this.title;
	}

	public void setTitle(String newValue) {
		_loadOnDemand("title", this.title, __DEFINITION__);
		_setProperty("title", this.title, this.title = newValue);
	}
	
	protected String subject;

	protected void initiateSubject() {
		this.subject = null;
	}

	public String getSubject() {
		_loadOnDemand("subject", this.subject, __DEFINITION__);
		return this.subject;
	}

	public void setSubject(String newValue) {
		_loadOnDemand("subject", this.subject, __DEFINITION__);
		_setProperty("subject", this.subject, this.subject = newValue);
	}
	
	protected String author;

	protected void initiateAuthor() {
		this.author = null;
	}

	public String getAuthor() {
		_loadOnDemand("author", this.author, __DEFINITION__);
		return this.author;
	}

	public void setAuthor(String newValue) {
		_loadOnDemand("author", this.author, __DEFINITION__);
		_setProperty("author", this.author, this.author = newValue);
	}
	
	protected String keywords;

	protected void initiateKeywords() {
		this.keywords = null;
	}

	public String getKeywords() {
		_loadOnDemand("keywords", this.keywords, __DEFINITION__);
		return this.keywords;
	}

	public void setKeywords(String newValue) {
		_loadOnDemand("keywords", this.keywords, __DEFINITION__);
		_setProperty("keywords", this.keywords, this.keywords = newValue);
	}

	protected String compress;

	protected void initiateCompress() {
		this.compress = null;
	}

	public String getCompress() {
		_loadOnDemand("compress", this.compress, __DEFINITION__);
		return this.compress;
	}

	public void setCompress(String newValue) {
		_loadOnDemand("compress", this.compress, __DEFINITION__);
		_setProperty("compress", this.compress, this.compress = newValue);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	protected void updateContent () {
		File file = getContent();
		String absolutePath = (file != null ? file.getAbsolutePath() : null);

//		this.setCompress(newValue);

		Map<String, Object> metadata = MetadataFiles.read(file);
		String contentType = (String)metadata.get(MetadataFiles.CONTENT_TYPE);
		contentType = (contentType != null ? contentType : toMimetype(file));
		
		setAuthor((String)metadata.get(MetadataFiles.AUTHOR));
		setLastModified((Date)metadata.get(MetadataFiles.LAST_MODIFIED));
		setCreationDate((Date)metadata.get(MetadataFiles.CREATION_DATE));
		setPrintDate((Date)metadata.get(MetadataFiles.PRINT_DATE));
		setEditTimeSeconds((Integer)metadata.get(MetadataFiles.EDIT_TIME));

		setRevisionNumber((Integer)metadata.get(MetadataFiles.REVISION_NUMBER));
		setWordCount((Integer)metadata.get(MetadataFiles.WORD_COUNT));
		setTableCount((Integer)metadata.get(MetadataFiles.TABLE_COUNT));
		setCharacterCount((Integer)metadata.get(MetadataFiles.CHARACTER_COUNT));
		setObjectCount((Integer)metadata.get(MetadataFiles.OBJECT_COUNT));
		setPageCount((Integer)metadata.get(MetadataFiles.PAGE_COUNT));
		setParagraphCount((Integer)metadata.get(MetadataFiles.PARAGRAPH_COUNT));
		setImageCount((Integer)metadata.get(MetadataFiles.IMAGE_COUNT));

		setTitle((String)metadata.get(MetadataFiles.TITLE));
		setSubject((String)metadata.get(MetadataFiles.SUBJECT));
		setComment((String)metadata.get(MetadataFiles.COMMENTS));
		setLanguage((String)metadata.get(MetadataFiles.LANGUAGE));
		setSoftware((String)metadata.get(MetadataFiles.SOFTWARE));
		setKeywords((String)metadata.get(MetadataFiles.KEYWORDS));

		setAudioCompressor((String)metadata.get(MetadataFiles.AUDIO_COMPRESSOR));
		setAudioChannelType((String)metadata.get(MetadataFiles.AUDIO_CHANNEL_TYPE));

		setChannels((Integer)metadata.get(MetadataFiles.CHANNELS));
		setSampleRate((String)metadata.get(MetadataFiles.SAMPLE_RATE));
		setFileVersion((String)metadata.get(MetadataFiles.VERSION));
		
		setTotalDataRate((Double)metadata.get(MetadataFiles.TOTAL_DATA_RATE));
		setVideoDataRate((Double)metadata.get(MetadataFiles.VIDEO_DATA_RATE));
		setAudioDataRate((Double)metadata.get(MetadataFiles.AUDIO_DATA_RATE));
		setDuration((Double)metadata.get(MetadataFiles.DURATION));
		setFrameRate((Double)metadata.get(MetadataFiles.FRAME_RATE));
		setByteLength((Double)metadata.get(MetadataFiles.BYTE_LENGTH));

		setWidth((Integer)metadata.get(MetadataFiles.WIDTH));
		setHeight((Integer)metadata.get(MetadataFiles.HEIGHT));

		setHasVideo((Boolean)metadata.get(MetadataFiles.HAS_VIDEO));
		setHasAudio((Boolean)metadata.get(MetadataFiles.HAS_AUDIO));

		setCanSeekOnTime((Boolean)metadata.get(MetadataFiles.CAN_SEEK_ON_TIME));
		setBitsPerSample((String)metadata.get(MetadataFiles.BITS_PER_SAMPLE));
		
		
		this.setContentType(contentType);
		this.setRegisterDate(new java.util.Date());
		this.setFormat(FilenameUtils.getExtension(absolutePath));
		
		String[] toHashes = FileHashes.getInstance().tryToHashes(file);
		this.setHash1((toHashes != null && toHashes.length > 0 ? toHashes[0] : null));
		this.setHash2((toHashes != null && toHashes.length > 1 ? toHashes[1] : null));
		this.setHash3((toHashes != null && toHashes.length > 2 ? toHashes[2] : null));
		
		this.setLastModification((file != null ? new java.util.Date(file.lastModified()) : null));
		this.setName(FilenameUtils.getName(absolutePath));
		this.setPath(absolutePath);
		this.setSize((file != null ? Long.valueOf(file.length()) : null));

		
		
//		this.setTitle(newValue);
//		this.setSubject(newValue);
//		this.setAuthor(newValue);
//		this.setDescription(newValue);
//		this.setKeywords(newValue);
//		this.setComment(newValue);
	}

	

	protected Integer width;

	protected void initiateWidth() {
		this.width = null;
	}

	public Integer getWidth() {
		_loadOnDemand("width", this.width, __DEFINITION__);
		return this.width;
	}

	public void setWidth(Integer newValue) {
		_loadOnDemand("width", this.width, __DEFINITION__);
		_setProperty("width", this.width, this.width = newValue);
	}
	
	protected Integer height;

	protected void initiateHeight() {
		this.height = null;
	}

	public Integer getHeight() {
		_loadOnDemand("height", this.height, __DEFINITION__);
		return this.height;
	}

	public void setHeight(Integer newValue) {
		_loadOnDemand("height", this.height, __DEFINITION__);
		_setProperty("height", this.height, this.height = newValue);
	}
	
	protected Boolean hasVideo;

	protected void initiateHasVideo() {
		this.hasVideo = null;
	}

	public Boolean getHasVideo() {
		_loadOnDemand("hasVideo", this.hasVideo, __DEFINITION__);
		return this.hasVideo;
	}

	public void setHasVideo(Boolean newValue) {
		_loadOnDemand("hasVideo", this.hasVideo, __DEFINITION__);
		_setProperty("hasVideo", this.hasVideo, this.hasVideo = newValue);
	}
	
	protected Boolean hasAudio;

	protected void initiateHasAudio() {
		this.hasAudio = null;
	}

	public Boolean getHasAudio() {
		_loadOnDemand("hasAudio", this.hasAudio, __DEFINITION__);
		return this.hasAudio;
	}

	public void setHasAudio(Boolean newValue) {
		_loadOnDemand("hasAudio", this.hasAudio, __DEFINITION__);
		_setProperty("hasAudio", this.hasAudio, this.hasAudio = newValue);
	}
	
	protected Boolean canSeekOnTime;

	protected void initiateCanSeekOnTime() {
		this.canSeekOnTime = null;
	}

	public Boolean getCanSeekOnTime() {
		_loadOnDemand("canSeekOnTime", this.canSeekOnTime, __DEFINITION__);
		return this.canSeekOnTime;
	}

	public void setCanSeekOnTime(Boolean newValue) {
		_loadOnDemand("canSeekOnTime", this.canSeekOnTime, __DEFINITION__);
		_setProperty("canSeekOnTime", this.canSeekOnTime, this.canSeekOnTime = newValue);
	}

	protected String bitsPerSample;

	protected void initiateBitsPerSample() {
		this.bitsPerSample = null;
	}

	public String getBitsPerSample() {
		_loadOnDemand("bitsPerSample", this.bitsPerSample, __DEFINITION__);
		return this.bitsPerSample;
	}

	public void setBitsPerSample(String newValue) {
		_loadOnDemand("bitsPerSample", this.bitsPerSample, __DEFINITION__);
		_setProperty("bitsPerSample", this.bitsPerSample, this.bitsPerSample = newValue);
	}
	
	protected Double totalDataRate;

	protected void initiateTotalDataRate() {
		this.totalDataRate = null;
	}

	public Double getTotalDataRate() {
		_loadOnDemand("totalDataRate", this.totalDataRate, __DEFINITION__);
		return this.totalDataRate;
	}

	public void setTotalDataRate(Double newValue) {
		_loadOnDemand("totalDataRate", this.totalDataRate, __DEFINITION__);
		_setProperty("totalDataRate", this.totalDataRate, this.totalDataRate = newValue);
	}

	protected Double videoDataRate;

	protected void initiateVideoDataRate() {
		this.videoDataRate = null;
	}

	public Double getVideoDataRate() {
		_loadOnDemand("videoDataRate", this.videoDataRate, __DEFINITION__);
		return this.videoDataRate;
	}

	public void setVideoDataRate(Double newValue) {
		_loadOnDemand("videoDataRate", this.videoDataRate, __DEFINITION__);
		_setProperty("videoDataRate", this.videoDataRate, this.videoDataRate = newValue);
	}

	protected Double audioDataRate;

	protected void initiateAudioDataRate() {
		this.audioDataRate = null;
	}

	public Double getAudioDataRate() {
		_loadOnDemand("audioDataRate", this.audioDataRate, __DEFINITION__);
		return this.audioDataRate;
	}

	public void setAudioDataRate(Double newValue) {
		_loadOnDemand("audioDataRate", this.audioDataRate, __DEFINITION__);
		_setProperty("audioDataRate", this.audioDataRate, this.audioDataRate = newValue);
	}

	protected Double duration;

	protected void initiateDuration() {
		this.duration = null;
	}

	public Double getDuration() {
		_loadOnDemand("duration", this.duration, __DEFINITION__);
		return this.duration;
	}

	public void setDuration(Double newValue) {
		_loadOnDemand("duration", this.duration, __DEFINITION__);
		_setProperty("duration", this.duration, this.duration = newValue);
	}

	protected Double frameRate;

	protected void initiateFrameRate() {
		this.frameRate = null;
	}

	public Double getFrameRate() {
		_loadOnDemand("frameRate", this.frameRate, __DEFINITION__);
		return this.frameRate;
	}

	public void setFrameRate(Double newValue) {
		_loadOnDemand("frameRate", this.frameRate, __DEFINITION__);
		_setProperty("frameRate", this.frameRate, this.frameRate = newValue);
	}

	protected Double byteLength;

	protected void initiateByteLength() {
		this.byteLength = null;
	}

	public Double getByteLength() {
		_loadOnDemand("byteLength", this.byteLength, __DEFINITION__);
		return this.byteLength;
	}

	public void setByteLength(Double newValue) {
		_loadOnDemand("byteLength", this.byteLength, __DEFINITION__);
		_setProperty("byteLength", this.byteLength, this.byteLength = newValue);
	}
	
	protected String fileVersion;

	protected void initiateFileVersion() {
		this.fileVersion = null;
	}

	public String getFileVersion() {
		_loadOnDemand("fileVersion", this.fileVersion, __DEFINITION__);
		return this.fileVersion;
	}

	public void setFileVersion(String newValue) {
		_loadOnDemand("fileVersion", this.fileVersion, __DEFINITION__);
		_setProperty("fileVersion", this.fileVersion, this.fileVersion = newValue);
	}
	
	protected String sampleRate;

	protected void initiateSampleRate() {
		this.sampleRate = null;
	}

	public String getSampleRate() {
		_loadOnDemand("sampleRate", this.sampleRate, __DEFINITION__);
		return this.sampleRate;
	}

	public void setSampleRate(String newValue) {
		_loadOnDemand("sampleRate", this.sampleRate, __DEFINITION__);
		_setProperty("sampleRate", this.sampleRate, this.sampleRate = newValue);
	}
	
	protected Integer channels;

	protected void initiateChannels() {
		this.channels = null;
	}

	public Integer getChannels() {
		_loadOnDemand("channels", this.channels, __DEFINITION__);
		return this.channels;
	}

	public void setChannels(Integer newValue) {
		_loadOnDemand("channels", this.channels, __DEFINITION__);
		_setProperty("channels", this.channels, this.channels = newValue);
	}
	
	protected String audioChannelType;

	protected void initiateAudioChannelType() {
		this.audioChannelType = null;
	}

	public String getAudioChannelType() {
		_loadOnDemand("audioChannelType", this.audioChannelType, __DEFINITION__);
		return this.audioChannelType;
	}

	public void setAudioChannelType(String newValue) {
		_loadOnDemand("audioChannelType", this.audioChannelType, __DEFINITION__);
		_setProperty("audioChannelType", this.audioChannelType, this.audioChannelType = newValue);
	}
	
	protected String audioCompressor;

	protected void initiateAudioCompressor() {
		this.audioCompressor = null;
	}

	public String getAudioCompressor() {
		_loadOnDemand("audioCompressor", this.audioCompressor, __DEFINITION__);
		return this.audioCompressor;
	}

	public void setAudioCompressor(String newValue) {
		_loadOnDemand("audioCompressor", this.audioCompressor, __DEFINITION__);
		_setProperty("audioCompressor", this.audioCompressor, this.audioCompressor = newValue);
	}
	
	protected String language;

	protected void initiateLanguage() {
		this.language = null;
	}

	public String getLanguage() {
		_loadOnDemand("language", this.language, __DEFINITION__);
		return this.language;
	}

	public void setLanguage(String newValue) {
		_loadOnDemand("language", this.language, __DEFINITION__);
		_setProperty("language", this.language, this.language = newValue);
	}

	protected String software;

	protected void initiateSoftware() {
		this.software = null;
	}

	public String getSoftware() {
		_loadOnDemand("software", this.software, __DEFINITION__);
		return this.software;
	}

	public void setSoftware(String newValue) {
		_loadOnDemand("software", this.software, __DEFINITION__);
		_setProperty("software", this.software, this.software = newValue);
	}
	
	protected Integer imageCount;

	protected void initiateImageCount() {
		this.imageCount = null;
	}

	public Integer getImageCount() {
		_loadOnDemand("imageCount", this.imageCount, __DEFINITION__);
		return this.imageCount;
	}

	public void setImageCount(Integer newValue) {
		_loadOnDemand("imageCount", this.imageCount, __DEFINITION__);
		_setProperty("imageCount", this.imageCount, this.imageCount = newValue);
	}
	
	protected Integer paragraphCount;

	protected void initiateParagraphCount() {
		this.paragraphCount = null;
	}

	public Integer getParagraphCount() {
		_loadOnDemand("paragraphCount", this.paragraphCount, __DEFINITION__);
		return this.paragraphCount;
	}

	public void setParagraphCount(Integer newValue) {
		_loadOnDemand("paragraphCount", this.paragraphCount, __DEFINITION__);
		_setProperty("paragraphCount", this.paragraphCount, this.paragraphCount = newValue);
	}
	
	protected Integer pageCount;

	protected void initiatePageCount() {
		this.pageCount = null;
	}

	public Integer getPageCount() {
		_loadOnDemand("pageCount", this.pageCount, __DEFINITION__);
		return this.pageCount;
	}

	public void setPageCount(Integer newValue) {
		_loadOnDemand("pageCount", this.pageCount, __DEFINITION__);
		_setProperty("pageCount", this.pageCount, this.pageCount = newValue);
	}
	
	protected Integer objectCount;

	protected void initiateObjectCount() {
		this.objectCount = null;
	}

	public Integer getObjectCount() {
		_loadOnDemand("objectCount", this.objectCount, __DEFINITION__);
		return this.objectCount;
	}

	public void setObjectCount(Integer newValue) {
		_loadOnDemand("objectCount", this.objectCount, __DEFINITION__);
		_setProperty("objectCount", this.objectCount, this.objectCount = newValue);
	}
	
	protected Integer characterCount;

	protected void initiateCharacterCount() {
		this.characterCount = null;
	}

	public Integer getCharacterCount() {
		_loadOnDemand("characterCount", this.characterCount, __DEFINITION__);
		return this.characterCount;
	}

	public void setCharacterCount(Integer newValue) {
		_loadOnDemand("characterCount", this.characterCount, __DEFINITION__);
		_setProperty("characterCount", this.characterCount, this.characterCount = newValue);
	}
	
	protected Integer tableCount;

	protected void initiateTableCount() {
		this.tableCount = null;
	}

	public Integer getTableCount() {
		_loadOnDemand("tableCount", this.tableCount, __DEFINITION__);
		return this.tableCount;
	}

	public void setTableCount(Integer newValue) {
		_loadOnDemand("tableCount", this.tableCount, __DEFINITION__);
		_setProperty("tableCount", this.tableCount, this.tableCount = newValue);
	}
	
	protected Integer wordCount;

	protected void initiateWordCount() {
		this.wordCount = null;
	}

	public Integer getWordCount() {
		_loadOnDemand("wordCount", this.wordCount, __DEFINITION__);
		return this.wordCount;
	}

	public void setWordCount(Integer newValue) {
		_loadOnDemand("wordCount", this.wordCount, __DEFINITION__);
		_setProperty("wordCount", this.wordCount, this.wordCount = newValue);
	}
	
	protected Integer revisionNumber;

	protected void initiateRevisionNumber() {
		this.revisionNumber = null;
	}

	public Integer getRevisionNumber() {
		_loadOnDemand("revisionNumber", this.revisionNumber, __DEFINITION__);
		return this.revisionNumber;
	}

	public void setRevisionNumber(Integer newValue) {
		_loadOnDemand("revisionNumber", this.revisionNumber, __DEFINITION__);
		_setProperty("revisionNumber", this.revisionNumber, this.revisionNumber = newValue);
	}
	
	protected Integer editTimeSeconds;

	protected void initiateEditTimeSeconds() {
		this.editTimeSeconds = null;
	}

	public Integer getEditTimeSeconds() {
		_loadOnDemand("editTimeSeconds", this.editTimeSeconds, __DEFINITION__);
		return this.editTimeSeconds;
	}

	public void setEditTimeSeconds(Integer newValue) {
		_loadOnDemand("editTimeSeconds", this.editTimeSeconds, __DEFINITION__);
		_setProperty("editTimeSeconds", this.editTimeSeconds, this.editTimeSeconds = newValue);
	}
	
	protected Date printDate;

	protected void initiatePrintDate() {
		this.printDate = null;
	}

	public Date getPrintDate() {
		_loadOnDemand("printDate", this.printDate, __DEFINITION__);
		return this.printDate;
	}

	public void setPrintDate(Date newValue) {
		_loadOnDemand("printDate", this.printDate, __DEFINITION__);
		_setProperty("printDate", this.printDate, this.printDate = newValue);
	}

	protected Date lastModified;

	protected void initiateLastModified() {
		this.lastModified = null;
	}

	public Date getLastModified() {
		_loadOnDemand("lastModified", this.lastModified, __DEFINITION__);
		return this.lastModified;
	}

	public void setLastModified(Date newValue) {
		_loadOnDemand("lastModified", this.lastModified, __DEFINITION__);
		_setProperty("lastModified", this.lastModified, this.lastModified = newValue);
	}

	protected Date creationDate;

	protected void initiateCreationDate() {
		this.creationDate = null;
	}

	public Date getCreationDate() {
		_loadOnDemand("creationDate", this.creationDate, __DEFINITION__);
		return this.creationDate;
	}

	public void setCreationDate(Date newValue) {
		_loadOnDemand("creationDate", this.creationDate, __DEFINITION__);
		_setProperty("creationDate", this.creationDate, this.creationDate = newValue);
	}
	
/*

	protected String name;
	protected String description;
	protected String comment;
	protected String contentType;
	protected String format;
	protected File content;
	protected Long size;
	protected String hash1;
	protected String hash2;
	protected String hash3;
	protected String path;
	protected Date registerDate;
	protected Date lastModification;
	protected Boolean embedded;
	protected String title;
	protected String subject;
	protected String author;
	protected String keywords;
	protected String compress;
	protected Integer width;
	protected Integer height;
	protected Boolean hasVideo;
	protected Boolean hasAudio;
	protected Boolean canSeekOnTime;
	protected String bitsPerSample;
	protected Double totalDataRate;
	protected Double videoDataRate;
	protected Double audioDataRate;
	protected Double duration;
	protected Double frameRate;
	protected Double byteLength;
	protected String fileVersion;
	protected String sampleRate;
	protected Integer channels;
	protected String audioChannelType;
	protected String audioCompressor;
	protected String language;
	protected String software;
	protected Integer imageCount;
	protected Integer paragraphCount;
	protected Integer pageCount;
	protected Integer objectCount;
	protected Integer characterCount;
	protected Integer tableCount;
	protected Integer wordCount;
	protected Integer revisionNumber;
	protected Integer editTimeSeconds;
	protected Date printDate;
	protected Date lastModified;
	protected Date creationDate;


 */
	
	
	protected void doWrite (com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Output out) {
		super.doWrite(kryo, out);
		
		kryo.writeObjectOrNull(out, this.hash1, String.class);
		kryo.writeObjectOrNull(out, this.hash2, String.class);
		kryo.writeObjectOrNull(out, this.hash3, String.class);
		kryo.writeObjectOrNull(out, this.registerDate, Date.class);
		kryo.writeObjectOrNull(out, this.lastModification, Date.class);
		kryo.writeObjectOrNull(out, this.title, String.class);
		kryo.writeObjectOrNull(out, this.subject, String.class);
		kryo.writeObjectOrNull(out, this.author, String.class);
		kryo.writeObjectOrNull(out, this.keywords, String.class);
		kryo.writeObjectOrNull(out, this.compress, String.class);
		kryo.writeObjectOrNull(out, this.width, Integer.class);
		kryo.writeObjectOrNull(out, this.height, Integer.class);
		kryo.writeObjectOrNull(out, this.hasVideo, Boolean.class);
		kryo.writeObjectOrNull(out, this.hasAudio, Boolean.class);
		kryo.writeObjectOrNull(out, this.canSeekOnTime, Boolean.class);
		kryo.writeObjectOrNull(out, this.bitsPerSample, String.class);
		kryo.writeObjectOrNull(out, this.totalDataRate, Double.class);
		kryo.writeObjectOrNull(out, this.videoDataRate, Double.class);
		kryo.writeObjectOrNull(out, this.audioDataRate, Double.class);
		kryo.writeObjectOrNull(out, this.duration, Double.class);
		kryo.writeObjectOrNull(out, this.frameRate, Double.class);
		kryo.writeObjectOrNull(out, this.byteLength, Double.class);
		kryo.writeObjectOrNull(out, this.fileVersion, String.class);
		kryo.writeObjectOrNull(out, this.sampleRate, String.class);
		kryo.writeObjectOrNull(out, this.channels, Integer.class);
		kryo.writeObjectOrNull(out, this.audioChannelType, String.class);
		kryo.writeObjectOrNull(out, this.audioCompressor, String.class);
		kryo.writeObjectOrNull(out, this.language, String.class);
		kryo.writeObjectOrNull(out, this.software, String.class);
		kryo.writeObjectOrNull(out, this.imageCount, Integer.class);
		kryo.writeObjectOrNull(out, this.paragraphCount, Integer.class);
		kryo.writeObjectOrNull(out, this.pageCount, Integer.class);
		kryo.writeObjectOrNull(out, this.objectCount, Integer.class);
		kryo.writeObjectOrNull(out, this.characterCount, Integer.class);
		kryo.writeObjectOrNull(out, this.tableCount, Integer.class);
		kryo.writeObjectOrNull(out, this.wordCount, Integer.class);
		kryo.writeObjectOrNull(out, this.revisionNumber, Integer.class);
		kryo.writeObjectOrNull(out, this.editTimeSeconds, Integer.class);
		kryo.writeObjectOrNull(out, this.printDate, Date.class);
		kryo.writeObjectOrNull(out, this.lastModified, Date.class);
		kryo.writeObjectOrNull(out, this.creationDate, Date.class);
	}
	
	protected void doRead(com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Input in) {
		super.doRead(kryo, in);
		
		this.hash1 = kryo.readObjectOrNull(in, String.class);
		this.hash2 = kryo.readObjectOrNull(in, String.class);
		this.hash3 = kryo.readObjectOrNull(in, String.class);
		this.registerDate = kryo.readObjectOrNull(in, Date.class);
		this.lastModification = kryo.readObjectOrNull(in, Date.class);
		this.title = kryo.readObjectOrNull(in, String.class);
		this.subject = kryo.readObjectOrNull(in, String.class);
		this.author = kryo.readObjectOrNull(in, String.class);
		this.keywords = kryo.readObjectOrNull(in, String.class);
		this.compress = kryo.readObjectOrNull(in, String.class);
		this.width = kryo.readObjectOrNull(in, Integer.class);
		this.height = kryo.readObjectOrNull(in, Integer.class);
		this.hasVideo = kryo.readObjectOrNull(in, Boolean.class);
		this.hasAudio = kryo.readObjectOrNull(in, Boolean.class);
		this.canSeekOnTime = kryo.readObjectOrNull(in, Boolean.class);
		this.bitsPerSample = kryo.readObjectOrNull(in, String.class);
		this.totalDataRate = kryo.readObjectOrNull(in, Double.class);
		this.videoDataRate = kryo.readObjectOrNull(in, Double.class);
		this.audioDataRate = kryo.readObjectOrNull(in, Double.class);
		this.duration = kryo.readObjectOrNull(in, Double.class);
		this.frameRate = kryo.readObjectOrNull(in, Double.class);
		this.byteLength = kryo.readObjectOrNull(in, Double.class);
		this.fileVersion = kryo.readObjectOrNull(in, String.class);
		this.sampleRate = kryo.readObjectOrNull(in, String.class);
		this.channels = kryo.readObjectOrNull(in, Integer.class);
		this.audioChannelType = kryo.readObjectOrNull(in, String.class);
		this.audioCompressor = kryo.readObjectOrNull(in, String.class);
		this.language = kryo.readObjectOrNull(in, String.class);
		this.software = kryo.readObjectOrNull(in, String.class);
		this.imageCount = kryo.readObjectOrNull(in, Integer.class);
		this.paragraphCount = kryo.readObjectOrNull(in, Integer.class);
		this.pageCount = kryo.readObjectOrNull(in, Integer.class);
		this.objectCount = kryo.readObjectOrNull(in, Integer.class);
		this.characterCount = kryo.readObjectOrNull(in, Integer.class);
		this.tableCount = kryo.readObjectOrNull(in, Integer.class);
		this.wordCount = kryo.readObjectOrNull(in, Integer.class);
		this.revisionNumber = kryo.readObjectOrNull(in, Integer.class);
		this.editTimeSeconds = kryo.readObjectOrNull(in, Integer.class);
		this.printDate = kryo.readObjectOrNull(in, Date.class);
		this.lastModified = kryo.readObjectOrNull(in, Date.class);
		this.creationDate = kryo.readObjectOrNull(in, Date.class);
	}
	
	
	
/*
	"FILE_NAME" public String getName() {
	"FILE_DESCRIPTION" public String getDescription() {
	"FILE_COMMENT" public String getComment() {
	"FILE_CONTENT_TYPE" public String getContentType() {
	"FILE_FORMAT" public String getFormat() {
	"FILE_SIZE" public Long getSize() {
	"FILE_PATH" public String getPath() {
	"FILE_EMBEDDED" public Boolean getEmbedded() {
	"FILE_CONTENT" @Lob LAZY org.effortless.model.FileUserType public File getContent() {
	"FILE_HASH1" public String getHash1() {
	"FILE_HASH2" public String getHash2() {
	"FILE_HASH3" public String getHash3() {
	"FILE_REGISTER_DATE" public Date getRegisterDate() {
	"FILE_LAST_MODIFICATION" public Date getLastModification() {
	"FILE_TITLE" public String getTitle() {
	"FILE_SUBJECT" public String getSubject() {
	"FILE_AUTHOR" public String getAuthor() {
	"FILE_KEYWORDS" public String getKeywords() {
	"FILE_COMPRESS" public String getCompress() {
	"FILE_WIDTH" public Integer getWidth() {
	"FILE_HEIGHT" public Integer getHeight() {
	"FILE_HAS_VIDEO" public Boolean getHasVideo() {
	"FILE_HAS_AUDIO" public Boolean getHasAudio() {
	"FILE_CAN_SEEK_ON_TIME" public Boolean getCanSeekOnTime() {
	"FILE_BITS_PER_SAMPLE" public String getBitsPerSample() {
	"FILE_TOTAL_DATA_RATE" public Double getTotalDataRate() {
	"FILE_VIDEO_DATA_RATE" public Double getVideoDataRate() {
	"FILE_AUDIO_DATA_RATE" public Double getAudioDataRate() {
	"FILE_DURATION" public Double getDuration() {
	"FILE_FRAME_RATE" public Double getFrameRate() {
	"FILE_BYTE_LENGTH" public Double getByteLength() {
	"FILE_VERSION" public String getFileVersion() {
	"FILE_SAMPLE_RATE" public String getSampleRate() {
	"FILE_CHANNELS" public Integer getChannels() {
	"FILE_AUDIO_CHANNEL_TYPE" public String getAudioChannelType() {
	"FILE_AUDIO_COMPRESSOR" public String getAudioCompressor() {
	"FILE_LANGUAGE" public String getLanguage() {
	"FILE_SOFTWARE" public String getSoftware() {
	"FILE_IMAGE_COUNT" public Integer getImageCount() {
	"FILE_PARAGRAPH_COUNT" public Integer getParagraphCount() {
	"FILE_PAGE_COUNT" public Integer getPageCount() {
	"FILE_OBJECT_COUNT" public Integer getObjectCount() {
	"FILE_CHARACTER_COUNT" public Integer getCharacterCount() {
	"FILE_TABLE_COUNT" public Integer getTableCount() {
	"FILE_WORD_COUNT" public Integer getWordCount() {
	"FILE_REVISION_NUMBER" public Integer getRevisionNumber() {
	"FILE_EDIT_TIME_SECONDS" public Integer getEditTimeSeconds() {
	"FILE_PRINT_DATE" public Date getPrintDate() {
	"FILE_LASTMODIFIED" public Date getLastModified() {
	"FILE_CREATIONDATE" public Date getCreationDate() {
*/
	
	
	protected String _columnsEager () {
		return "FILE_HASH1, FILE_HASH2, FILE_HASH3, FILE_REGISTER_DATE, FILE_LAST_MODIFICATION, FILE_TITLE, FILE_SUBJECT, FILE_AUTHOR, FILE_KEYWORDS, FILE_COMPRESS, FILE_WIDTH, FILE_HEIGHT, FILE_HAS_VIDEO, FILE_HAS_AUDIO, FILE_CAN_SEEK_ON_TIME, FILE_BITS_PER_SAMPLE, FILE_TOTAL_DATA_RATE, FILE_VIDEO_DATA_RATE, FILE_AUDIO_DATA_RATE, FILE_DURATION, FILE_FRAME_RATE, FILE_BYTE_LENGTH, FILE_VERSION, FILE_SAMPLE_RATE, FILE_CHANNELS, FILE_AUDIO_CHANNEL_TYPE, FILE_AUDIO_COMPRESSOR, FILE_LANGUAGE, FILE_SOFTWARE, FILE_IMAGE_COUNT, FILE_PARAGRAPH_COUNT, FILE_PAGE_COUNT, FILE_OBJECT_COUNT, FILE_CHARACTER_COUNT, FILE_TABLE_COUNT, FILE_WORD_COUNT, FILE_REVISION_NUMBER, FILE_EDIT_TIME_SECONDS, FILE_PRINT_DATE, FILE_LASTMODIFIED, FILE_CREATIONDATE";
	}

//	protected String _columnsLazy () {
//		return "FILE_CONTENT";
//	}
	
	protected Object _newInstance () {
		return new MediaFileEntity();
	}

	protected void _loadEager (Object target, DbManager db, ResultSet rs) {
		super._loadEager(target, db, rs);
		MediaFileEntity result = (MediaFileEntity)target;

		result.hash1 = (String)__DEFINITION__.loadValue("FILE_HASH1", rs);
		result._setupLoaded("hash1");

		result.hash2 = (String)__DEFINITION__.loadValue("FILE_HASH2", rs);
		result._setupLoaded("hash2");

		result.hash3 = (String)__DEFINITION__.loadValue("FILE_HASH3", rs);
		result._setupLoaded("hash3");

		result.registerDate = (Date)__DEFINITION__.loadValue("FILE_REGISTER_DATE", rs);
		result._setupLoaded("registerDate");

		result.lastModification = (Date)__DEFINITION__.loadValue("FILE_LAST_MODIFICATION", rs);
		result._setupLoaded("lastModification");

		result.title = (String)__DEFINITION__.loadValue("FILE_TITLE", rs);
		result._setupLoaded("title");

		result.subject = (String)__DEFINITION__.loadValue("FILE_SUBJECT", rs);
		result._setupLoaded("subject");

		result.author = (String)__DEFINITION__.loadValue("FILE_AUTHOR", rs);
		result._setupLoaded("author");

		result.keywords = (String)__DEFINITION__.loadValue("FILE_KEYWORDS", rs);
		result._setupLoaded("keywords");

		result.compress = (String)__DEFINITION__.loadValue("FILE_COMPRESS", rs);
		result._setupLoaded("compress");

		result.width = (Integer)__DEFINITION__.loadValue("FILE_WIDTH", rs);
		result._setupLoaded("width");

		result.height = (Integer)__DEFINITION__.loadValue("FILE_HEIGHT", rs);
		result._setupLoaded("height");

		result.hasVideo = (Boolean)__DEFINITION__.loadValue("FILE_HAS_VIDEO", rs);
		result._setupLoaded("hasVideo");

		result.hasAudio = (Boolean)__DEFINITION__.loadValue("FILE_HAS_AUDIO", rs);
		result._setupLoaded("hasAudio");

		result.canSeekOnTime = (Boolean)__DEFINITION__.loadValue("FILE_CAN_SEEK_ON_TIME", rs);
		result._setupLoaded("canSeekOnTime");

		result.bitsPerSample = (String)__DEFINITION__.loadValue("FILE_BITS_PER_SAMPLE", rs);
		result._setupLoaded("bitsPerSample");

		result.totalDataRate = (Double)__DEFINITION__.loadValue("FILE_TOTAL_DATA_RATE", rs);
		result._setupLoaded("totalDataRate");

		result.videoDataRate = (Double)__DEFINITION__.loadValue("FILE_VIDEO_DATA_RATE", rs);
		result._setupLoaded("videoDataRate");

		result.audioDataRate = (Double)__DEFINITION__.loadValue("FILE_AUDIO_DATA_RATE", rs);
		result._setupLoaded("audioDataRate");

		result.duration = (Double)__DEFINITION__.loadValue("FILE_DURATION", rs);
		result._setupLoaded("duration");

		result.frameRate = (Double)__DEFINITION__.loadValue("FILE_FRAME_RATE", rs);
		result._setupLoaded("frameRate");

		result.byteLength = (Double)__DEFINITION__.loadValue("FILE_BYTE_LENGTH", rs);
		result._setupLoaded("byteLength");

		result.fileVersion = (String)__DEFINITION__.loadValue("FILE_VERSION", rs);
		result._setupLoaded("fileVersion");

		result.sampleRate = (String)__DEFINITION__.loadValue("FILE_SAMPLE_RATE", rs);
		result._setupLoaded("sampleRate");

		result.channels = (Integer)__DEFINITION__.loadValue("FILE_CHANNELS", rs);
		result._setupLoaded("channels");

		result.audioChannelType = (String)__DEFINITION__.loadValue("FILE_AUDIO_CHANNEL_TYPE", rs);
		result._setupLoaded("audioChannelType");

		result.audioCompressor = (String)__DEFINITION__.loadValue("FILE_AUDIO_COMPRESSOR", rs);
		result._setupLoaded("audioCompressor");

		result.language = (String)__DEFINITION__.loadValue("FILE_LANGUAGE", rs);
		result._setupLoaded("language");

		result.software = (String)__DEFINITION__.loadValue("FILE_SOFTWARE", rs);
		result._setupLoaded("software");

		result.imageCount = (Integer)__DEFINITION__.loadValue("FILE_IMAGE_COUNT", rs);
		result._setupLoaded("imageCount");

		result.paragraphCount = (Integer)__DEFINITION__.loadValue("FILE_PARAGRAPH_COUNT", rs);
		result._setupLoaded("paragraphCount");

		result.pageCount = (Integer)__DEFINITION__.loadValue("FILE_PAGE_COUNT", rs);
		result._setupLoaded("pageCount");

		result.objectCount = (Integer)__DEFINITION__.loadValue("FILE_OBJECT_COUNT", rs);
		result._setupLoaded("objectCount");

		result.characterCount = (Integer)__DEFINITION__.loadValue("FILE_CHARACTER_COUNT", rs);
		result._setupLoaded("characterCount");

		result.tableCount = (Integer)__DEFINITION__.loadValue("FILE_TABLE_COUNT", rs);
		result._setupLoaded("tableCount");

		result.wordCount = (Integer)__DEFINITION__.loadValue("FILE_WORD_COUNT", rs);
		result._setupLoaded("wordCount");

		result.revisionNumber = (Integer)__DEFINITION__.loadValue("FILE_REVISION_NUMBER", rs);
		result._setupLoaded("revisionNumber");

		result.editTimeSeconds = (Integer)__DEFINITION__.loadValue("FILE_EDIT_TIME_SECONDS", rs);
		result._setupLoaded("editTimeSeconds");

		result.printDate = (Date)__DEFINITION__.loadValue("FILE_PRINT_DATE", rs);
		result._setupLoaded("printDate");

		result.lastModified = (Date)__DEFINITION__.loadValue("FILE_LASTMODIFIED", rs);
		result._setupLoaded("lastModified");

		result.creationDate = (Date)__DEFINITION__.loadValue("FILE_CREATIONDATE", rs);
		result._setupLoaded("creationDate");
	}
	
	protected void _loadLazy (Object target, DbManager db, ResultSet rs) {
		super._loadLazy(target, db, rs);
	}
	
	protected static final MediaFileEntity _pivot = new MediaFileEntity();
	
	protected static final String _TABLE = "MEDIA_FILES";
	protected static final String _SEQ = _TABLE + "_SEQ";
	public static final EntityDefinition __DEFINITION__ = new EntityDefinition()
		.setTableName(_TABLE)
		.setSequenceName(_SEQ)
		.addParent(FileEntity.__DEFINITION__)
		.addProperty("hash1", "FILE_HASH1", String.class, "1024", null, "EAGER")
		.addProperty("hash2", "FILE_HASH2", String.class, "1024", null, "EAGER")
		.addProperty("hash3", "FILE_HASH3", String.class, "1024", null, "EAGER")
		.addProperty("registerDate", "FILE_REGISTER_DATE", Date.class, null, null, "EAGER")
		.addProperty("lastModification", "FILE_LAST_MODIFICATION", Date.class, null, null, "EAGER")
		.addProperty("title", "FILE_TITLE", String.class, "255", null, "EAGER")
		.addProperty("subject", "FILE_SUBJECT", String.class, "255", null, "EAGER")
		.addProperty("author", "FILE_AUTHOR", String.class, "255", null, "EAGER")
		.addProperty("keywords", "FILE_KEYWORDS", String.class, "255", null, "EAGER")
		.addProperty("compress", "FILE_COMPRESS", String.class, "255", null, "EAGER")

		.addProperty("width", "FILE_WIDTH", Integer.class, null, null, "EAGER")
		.addProperty("height", "FILE_HEIGHT", Integer.class, null, null, "EAGER")
		.addProperty("hasVideo", "FILE_HAS_VIDEO", Boolean.class, null, null, "EAGER")
		.addProperty("hasAudio", "FILE_HAS_AUDIO", Boolean.class, null, null, "EAGER")
		.addProperty("canSeekOnTime", "FILE_CAN_SEEK_ON_TIME", Boolean.class, null, null, "EAGER")
		.addProperty("bitsPerSample", "FILE_BITS_PER_SAMPLE", String.class, "255", null, "EAGER")

		.addProperty("totalDataRate", "FILE_TOTAL_DATA_RATE", Double.class, null, null, "EAGER")
		.addProperty("videoDataRate", "FILE_VIDEO_DATA_RATE", Double.class, null, null, "EAGER")
		.addProperty("audioDataRate", "FILE_AUDIO_DATA_RATE", Double.class, null, null, "EAGER")
		.addProperty("duration", "FILE_DURATION", Double.class, null, null, "EAGER")
		.addProperty("frameRate", "FILE_FRAME_RATE", Double.class, null, null, "EAGER")
		.addProperty("byteLength", "FILE_BYTE_LENGTH", Double.class, null, null, "EAGER")
		
		.addProperty("fileVersion", "FILE_VERSION", String.class, "255", null, "EAGER")
		.addProperty("sampleRate", "FILE_SAMPLE_RATE", String.class, "255", null, "EAGER")

		.addProperty("channels", "FILE_CHANNELS", Integer.class, null, null, "EAGER")
		
		.addProperty("audioChannelType", "FILE_AUDIO_CHANNEL_TYPE", String.class, "255", null, "EAGER")
		.addProperty("audioCompressor", "FILE_AUDIO_COMPRESSOR", String.class, "255", null, "EAGER")
		.addProperty("language", "FILE_LANGUAGE", String.class, "255", null, "EAGER")
		.addProperty("software", "FILE_SOFTWARE", String.class, "255", null, "EAGER")

		.addProperty("imageCount", "FILE_IMAGE_COUNT", Integer.class, null, null, "EAGER")
		.addProperty("paragraphCount", "FILE_PARAGRAPH_COUNT", Integer.class, null, null, "EAGER")
		.addProperty("pageCount", "FILE_PAGE_COUNT", Integer.class, null, null, "EAGER")
		.addProperty("objectCount", "FILE_OBJECT_COUNT", Integer.class, null, null, "EAGER")
		.addProperty("characterCount", "FILE_CHARACTER_COUNT", Integer.class, null, null, "EAGER")
		.addProperty("tableCount", "FILE_TABLE_COUNT", Integer.class, null, null, "EAGER")
		.addProperty("wordCount", "FILE_WORD_COUNT", Integer.class, null, null, "EAGER")
		.addProperty("revisionNumber", "FILE_REVISION_NUMBER", Integer.class, null, null, "EAGER")
		.addProperty("editTimeSeconds", "FILE_EDIT_TIME_SECONDS", Integer.class, null, null, "EAGER")

		.addProperty("printDate", "FILE_PRINT_DATE", Date.class, null, null, "EAGER")
		.addProperty("lastModified", "FILE_LASTMODIFIED", Date.class, null, null, "EAGER")
		.addProperty("creationDate", "FILE_CREATIONDATE", Date.class, null, null, "EAGER")
		
		.setDefaultLoader(new EagerPropertiesLoader(MediaFileEntity._pivot))
		.addLoader(new LazyPropertiesLoader(MediaFileEntity._pivot));

		
		
	
//	public static AbstractFilter<AbstractCfg> listBy () {
//		return AbstractCfg.listBy(AbstractCfg.class);
//	}

	public static Filter listAll () {
		return EntityFilter.buildEntityFilter(__DEFINITION__, __DEFINITION__.getDefaultLoader());
	}	

	@Override
	protected Object[] _getAllParameterChanges() {
		return new Object[] {new String[] {"FILE_HASH1", "FILE_HASH2", "FILE_HASH3", "FILE_REGISTER_DATE", "FILE_LAST_MODIFICATION", "FILE_TITLE", "FILE_SUBJECT", "FILE_AUTHOR", "FILE_KEYWORDS", "FILE_COMPRESS", "FILE_WIDTH", "FILE_HEIGHT", "FILE_HAS_VIDEO", "FILE_HAS_AUDIO", "FILE_CAN_SEEK_ON_TIME", "FILE_BITS_PER_SAMPLE", "FILE_TOTAL_DATA_RATE", "FILE_VIDEO_DATA_RATE", "FILE_AUDIO_DATA_RATE", "FILE_DURATION", "FILE_FRAME_RATE", "FILE_BYTE_LENGTH", "FILE_VERSION", "FILE_SAMPLE_RATE", "FILE_CHANNELS", "FILE_AUDIO_CHANNEL_TYPE", "FILE_AUDIO_COMPRESSOR", "FILE_LANGUAGE", "FILE_SOFTWARE", "FILE_IMAGE_COUNT", "FILE_PARAGRAPH_COUNT", "FILE_PAGE_COUNT", "FILE_OBJECT_COUNT", "FILE_CHARACTER_COUNT", "FILE_TABLE_COUNT", "FILE_WORD_COUNT", "FILE_REVISION_NUMBER", "FILE_EDIT_TIME_SECONDS", "FILE_PRINT_DATE", "FILE_LASTMODIFIED", "FILE_CREATIONDATE", "ID"}, new Object[] {this.hash1, this.hash2, this.hash3,	this.registerDate, this.lastModification, this.title, this.subject, this.author, this.keywords, this.compress, this.width, this.height, this.hasVideo, this.hasAudio, this.canSeekOnTime, this.bitsPerSample, this.totalDataRate, this.videoDataRate, this.audioDataRate, this.duration, this.frameRate, this.byteLength, this.fileVersion, this.sampleRate, this.channels, this.audioChannelType, this.audioCompressor, this.language, this.software, this.imageCount, this.paragraphCount, this.pageCount, this.objectCount, this.characterCount, this.tableCount, this.wordCount, this.revisionNumber, this.editTimeSeconds, this.printDate, this.lastModified, this.creationDate, this.id}};
	}

}
