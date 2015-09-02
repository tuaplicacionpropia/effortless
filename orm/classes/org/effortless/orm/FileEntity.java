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


import org.effortless.core.Collections;
import org.effortless.core.FileUtils;
import org.effortless.core.FilenameUtils;
import org.effortless.core.Hex;
import org.effortless.core.IOUtils;
import org.effortless.core.MetadataFiles;
import org.effortless.core.ObjectUtils;
import org.effortless.core.UnusualException;
import org.effortless.orm.definition.EntityDefinition;
import org.effortless.orm.impl.ColumnExtraType;
import org.effortless.orm.impl.EntityFilter;
import org.effortless.orm.impl.PropertiesLoader;
import org.effortless.orm.util.FileHashes;
//import org.hibernate.annotations.Tuplizer;
//import org.hibernate.tuple.entity.PojoEntityTuplizer;

//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.io.Output;


//http://code.google.com/p/xxhash/ Para calcular huellas
//https://github.com/jpountz/lz4-java Para comprimir con rapidez  (http://fastcompression.blogspot.com.es/p/lz4.html)
//@Entity
//@Table(name="APP_FILES")
//@javax.persistence.SequenceGenerator(name="sequence_id", sequenceName="app_files")
//@MappedSuperclass
//@Tuplizer(impl = FileEntityTuplizer.class)
public class FileEntity extends AbstractIdEntity implements IFile {

	public FileEntity () {
		super();
	}
	
	public FileEntity (Long id) {
		super(id);
	}
	
	protected void initiate () {
		super.initiate();

		initiateName ();
		initiateDescription ();
		initiateComment ();
		initiateContentType ();
		initiateFormat();
		initiateSize();
		initiatePath();
		initiateEmbedded();
		
		initiateContent();
	}

	protected EntityDefinition _doGetEntityDefinition() {
		return FileEntity.__DEFINITION__;
	}

	protected String name;
	
	protected void initiateName () {
		this.name = null;
	}
	
	public String getName() {
		_loadOnDemand("name", this.name);
		return this.name;
	}
	
	public void setName(String newValue) {
		_loadOnDemand("name", this.name);
		_setProperty("name", this.name, this.name = newValue);
	}

	protected String description;
	
	protected void initiateDescription () {
		this.description = null;
	}
	
	public String getDescription() {
		_loadOnDemand("description", this.description);
		return this.description;
	}

	public void setDescription(String newValue) {
		_loadOnDemand("description", this.description);
		_setProperty("description", this.description, this.description = newValue);
	}

	protected String comment;
	
	protected void initiateComment () {
		this.comment = null;
	}
	
	public String getComment() {
		_loadOnDemand("comment", this.comment);
		return this.comment;
	}

	public void setComment(String newValue) {
		_loadOnDemand("comment", this.comment);
		_setProperty("comment", this.comment, this.comment = newValue);
	}

	protected String contentType;
	
	protected void initiateContentType () {
		this.contentType = null;
	}
	
	public String getContentType() {
		_loadOnDemand("contentType", this.contentType);
		return this.contentType;
	}

	public void setContentType(String newValue) {
		_loadOnDemand("contentType", this.contentType);
		_setProperty("contentType", this.contentType, this.contentType = newValue);
	}

	protected String format;

	protected void initiateFormat() {
		this.format = null;
	}

	public String getFormat() {
		_loadOnDemand("format", this.format);
		return this.format;
	}

	public void setFormat(String newValue) {
		_loadOnDemand("format", this.format);
		_setProperty("format", this.format, this.format = newValue);
	}

	protected File content;

	protected void initiateContent() {
		this.content = null;
	}

	public File getContent() {
		_loadOnDemand("content", this.content);
		return this.content;
	}

	public void setContent(File newValue) {
		_loadOnDemand("content", this.content);
		boolean change = _setProperty("content", this.content, this.content = newValue);
		if (change) {
			updateContent();
		}
	}

	protected void _doChangeProperty (String propertyName, Object oldValue, Object newValue) {
		if ("content".equals(propertyName)) {
			updateContent();
		}
	}
	
	
//	protected String toMimetype (File file) {
//		String result = null;
//		result = new MimetypesFileTypeMap().getContentType(file);
//	    return result;
//	}
	
	protected String toMimetype(File file) {
		String result = null;
		if (file != null) {
			String fileUrl = file.getAbsolutePath();
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			result = fileNameMap.getContentTypeFor(fileUrl);
		}
		return result;
	}
	
	public void setContent (String file) {
		File _file = new File(file);
		setContent(_file);
	}
	
	public void setContent (java.io.Reader reader) {
		File _file = null;
		try {
			_file = File.createTempFile(".file", ".tmp");
		} catch (IOException e) {
			throw new UnusualException(e);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_file);
		} catch (FileNotFoundException e) {
			throw new UnusualException(e);
		}
		try {
			IOUtils.copy(reader, fos);
		} catch (IOException e) {
			throw new UnusualException(e);
		}
		
		IOUtils.closeQuietly(reader);
		IOUtils.closeQuietly(fos);
	}
	
	public void setContent (java.io.InputStream input) {
		File _file = null;
		try {
			_file = File.createTempFile(".file", ".tmp");
		} catch (IOException e) {
			throw new UnusualException(e);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_file);
		} catch (FileNotFoundException e) {
			throw new UnusualException(e);
		}
		try {
			IOUtils.copy(input, fos);
		} catch (IOException e) {
			throw new UnusualException(e);
		}

		IOUtils.closeQuietly(input);
		IOUtils.closeQuietly(fos);
		
		this.content = _file;
	}

	public void setContent (java.io.InputStream input, String name) {
		String className = this.getClass().getSimpleName();
		File _folder = FileUtils.createTempFolder(className);
		
		File _file = new File(_folder, name);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(_file);
		} catch (FileNotFoundException e) {
			throw new UnusualException(e);
		}
		try {
			IOUtils.copy(input, fos);
		} catch (IOException e) {
			throw new UnusualException(e);
		}

		IOUtils.closeQuietly(input);
		IOUtils.closeQuietly(fos);
		
		this.setContent(_file);
//		this.persist();
	}

	public boolean hasContent () {
		boolean result = false;
//		File content = getContent();
//		result = (content != null ? content.exists() : false);
		Long size = this.getSize();
		result = (size != null && size.longValue() > 0L);
		return result;
	}
	
	public void clearContent () {
		setContent((File)null);
	}
	
//	@javax.persistence.Transient
	public boolean isClearContent () {
		boolean result = false;
		File content = getContent();
		result = (content == null);
		return result;
	}

	protected Long size;

	protected void initiateSize() {
		this.size = null;
	}

	public Long getSize() {
		_loadOnDemand("size", this.size);
		return this.size;
	}

	public void setSize(Long newValue) {
		_loadOnDemand("size", this.size);
		_setProperty("size", this.size, this.size = newValue);
	}

//	@javax.persistence.Transient
	public String getSizeText () {
		String result = null;
		if (this.size != null) {
			boolean typeUnit = false;
			long bytes = this.size.longValue();
			
			int unit = typeUnit ? 1000 : 1024;
			
			if (bytes < unit) {
				result = bytes + " B";
			}
			else {
			    int exp = (int) (Math.log(bytes) / Math.log(unit));
			    String pre = (typeUnit ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (typeUnit ? "" : ""/*"i"*/);
			    result = String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
			}
		}
		return result;
	}
	
	protected String path;

	protected void initiatePath() {
		this.path = null;
	}

	public String getPath() {
		_loadOnDemand("path", this.path);
		return this.path;
	}

	public void setPath(String newValue) {
		_loadOnDemand("path", this.path);
		_setProperty("path", this.path, this.path = newValue);
	}
	
	protected Boolean embedded;

	protected void initiateEmbedded() {
		this.embedded = Boolean.TRUE;
	}

	public Boolean getEmbedded() {
		_loadOnDemand("embedded", this.embedded);
		return this.embedded;
	}

	public void setEmbedded(Boolean newValue) {
		_loadOnDemand("embedded", this.embedded);
		_setProperty("embedded", this.embedded, this.embedded = newValue);
	}

	public String toString () {
		String result = null;
		String path = getPath();
		String sizeText = getSizeText();
		path = (path != null ? path : "");
		sizeText = (sizeText != null ? sizeText : "");
		result = path + " " + sizeText;
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	protected void updateContent () {
		File file = getContent();
		String absolutePath = (file != null ? file.getAbsolutePath() : null);

//		this.setCompress(newValue);

		Map<String, Object> metadata = MetadataFiles.read(file);
		String contentType = (String)metadata.get(MetadataFiles.CONTENT_TYPE);
		contentType = (contentType != null ? contentType : toMimetype(file));
		
		this.setContentType(contentType);
		this.setFormat(FilenameUtils.getExtension(absolutePath));
		
		this.setName(FilenameUtils.getName(absolutePath));
		this.setPath(absolutePath);
		this.setSize((file != null ? Long.valueOf(file.length()) : null));
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
		
		kryo.writeObjectOrNull(out, this.name, String.class);
		kryo.writeObjectOrNull(out, this.description, String.class);
		kryo.writeObjectOrNull(out, this.comment, String.class);
		kryo.writeObjectOrNull(out, this.contentType, String.class);
		kryo.writeObjectOrNull(out, this.format, String.class);
		kryo.writeObjectOrNull(out, this.size, Long.class);
		kryo.writeObjectOrNull(out, this.path, String.class);
		kryo.writeObjectOrNull(out, this.embedded, Boolean.class);

//		kryo.writeObjectOrNull(out, this.content, File.class);
		kryo.writeObjectOrNull(out, (this.content != null ? this.content.getAbsolutePath() : null), String.class);
		
	}
	
	protected void doRead(com.esotericsoftware.kryo.Kryo kryo, com.esotericsoftware.kryo.io.Input in) {
		super.doRead(kryo, in);
		
		this.name = kryo.readObjectOrNull(in, String.class);
		this.description = kryo.readObjectOrNull(in, String.class);
		this.comment = kryo.readObjectOrNull(in, String.class);
		this.contentType = kryo.readObjectOrNull(in, String.class);
		this.format = kryo.readObjectOrNull(in, String.class);
		this.size = kryo.readObjectOrNull(in, Long.class);
		this.path = kryo.readObjectOrNull(in, String.class);
		this.embedded = kryo.readObjectOrNull(in, Boolean.class);

		String contentPath = kryo.readObjectOrNull(in, String.class);
		this.content = (contentPath != null ? new java.io.File(contentPath) : null);
		
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
		return _concatPropertiesLoader("FILE_NAME, FILE_DESCRIPTION, FILE_COMMENT, FILE_CONTENT_TYPE, FILE_FORMAT, FILE_SIZE, FILE_PATH, FILE_EMBEDDED", super._columnsEager());
	}

	protected String _columnsLazy () {
		return _concatPropertiesLoader("FILE_CONTENT", super._columnsLazy());
	}
	
	protected Object _newInstance () {
		return new FileEntity();
	}

	protected void _loadEager (Object target, DbManager db, ResultSet rs) {
		super._loadEager(target, db, rs);
		FileEntity result = (FileEntity)target;

		result.name = (String)__DEFINITION__.loadValue("FILE_NAME", rs);
		result._setupLoaded("name");

		result.description = (String)__DEFINITION__.loadValue("FILE_DESCRIPTION", rs);
		result._setupLoaded("description");

		result.comment = (String)__DEFINITION__.loadValue("FILE_COMMENT", rs);
		result._setupLoaded("comment");

		result.contentType = (String)__DEFINITION__.loadValue("FILE_CONTENT_TYPE", rs);
		result._setupLoaded("contentType");
		
		result.format = (String)__DEFINITION__.loadValue("FILE_FORMAT", rs);
		result._setupLoaded("format");

		result.size = (Long)__DEFINITION__.loadValue("FILE_SIZE", rs);
		result._setupLoaded("size");

		result.path = (String)__DEFINITION__.loadValue("FILE_PATH", rs);
		result._setupLoaded("path");

		result.embedded = (Boolean)__DEFINITION__.loadValue("FILE_EMBEDDED", rs);
		result._setupLoaded("embedded");
	}
	
	protected void _loadLazy (Object target, DbManager db, ResultSet rs) {
		super._loadLazy(target, db, rs);
		FileEntity result = (FileEntity)target;

		result.content = (java.io.File)__DEFINITION__.loadValue("FILE_CONTENT", rs);
		result._setupLoaded("content");
	}
	
	protected static final FileEntity _pivot = new FileEntity();
	
	protected static final String _TABLE = "FILES";
	protected static final String _SEQ = _TABLE + "_SEQ";
	public static final EntityDefinition __DEFINITION__ = new EntityDefinition()
		.setTableName(_TABLE)
		.setSequenceName(_SEQ)
		.addParent(AbstractIdEntity.__DEFINITION__)
		.addProperty("name", "FILE_NAME", String.class, "255", null, "EAGER")
		.addProperty("description", "FILE_DESCRIPTION", String.class, "255", null, "EAGER")
		.addProperty("comment", "FILE_COMMENT", String.class, "3072", null, "EAGER")
		.addProperty("contentType", "FILE_CONTENT_TYPE", String.class, "255", null, "EAGER")
		.addProperty("format", "FILE_FORMAT", String.class, "255", null, "EAGER")
		.addProperty("size", "FILE_SIZE", Long.class, null, null, "EAGER")
		.addProperty("path", "FILE_PATH", String.class, "1024", null, "EAGER")
		.addProperty("embedded", "FILE_EMBEDDED", Boolean.class, null, null, "EAGER")

		.addFileProperty("content", "FILE_CONTENT", java.io.File.class, null, "LAZY")
		
		.setDefaultOrderBy("FILE_NAME ASC, ID ASC")
		.setDefaultLoader(new EagerPropertiesLoader(FileEntity._pivot))
		.addLoader(new LazyPropertiesLoader(FileEntity._pivot));

		
		
	
//	public static AbstractFilter<AbstractCfg> listBy () {
//		return AbstractCfg.listBy(AbstractCfg.class);
//	}

	public static Filter listAll () {
		return EntityFilter.buildEntityFilter(__DEFINITION__);
	}

	@Override
	protected Object[] _getAllParameterChanges() {
		return _concatAllParameterChanges(new Object[] {Collections.asList("FILE_NAME", "FILE_DESCRIPTION", "FILE_COMMENT", "FILE_CONTENT_TYPE", "FILE_FORMAT", "FILE_SIZE", "FILE_PATH", "FILE_EMBEDDED", "FILE_CONTENT"), Collections.asList(this.name, this.description, this.comment, this.contentType, this.format, this.size, this.path, this.embedded, this.content)}, super._getAllParameterChanges());
	}

}
