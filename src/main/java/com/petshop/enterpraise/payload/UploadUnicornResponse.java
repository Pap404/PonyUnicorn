package com.petshop.enterpraise.payload;

public class UploadUnicornResponse {
    private String unicornFileName;
    private String unicornFileDownloadUri;
    private String unicornFileType;
    private long unicornSize;

    public UploadUnicornResponse(String unicornFileName, String unicornFileDownloadUri, String unicornFileType, long unicornSize) {
        this.unicornFileName = unicornFileName;
        this.unicornFileDownloadUri = unicornFileDownloadUri;
        this.unicornFileType = unicornFileType;
        this.unicornSize = unicornSize;
    }

    public String getUnicornFileName() {
        return unicornFileName;
    }

    public void setUnicornFileName(String unicornFileName) {
        this.unicornFileName = unicornFileName;
    }

    public String getUnicornFileDownloadUri() {
        return unicornFileDownloadUri;
    }

    public void setUnicornFileDownloadUri(String unicornFileDownloadUri) {
        this.unicornFileDownloadUri = unicornFileDownloadUri;
    }

    public String getUnicornFileType() {
        return unicornFileType;
    }

    public void setUnicornFileType(String unicornFileType) {
        this.unicornFileType = unicornFileType;
    }

    public long getUnicornSize() {
        return unicornSize;
    }

    public void setUnicornSize(long unicornSize) {
        this.unicornSize = unicornSize;
    }
}
