package com.petshop.enterpraise.payload;

public class UploadElfResponse {
    private String elfFileName;
    private String elfFileDownloadUri;
    private String elfFileType;
    private long elfSize;

    public UploadElfResponse(String elfFileName, String elfFileDownloadUri, String elfFileType, long elfSize) {
        this.elfFileName = elfFileName;
        this.elfFileDownloadUri = elfFileDownloadUri;
        this.elfFileType = elfFileType;
        this.elfSize = elfSize;
    }

    public String getElfFileName() {
        return elfFileName;
    }

    public void setElfFileName(String elfFileName) {
        this.elfFileName = elfFileName;
    }

    public String getElfFileDownloadUri() {
        return elfFileDownloadUri;
    }

    public void setElfFileDownloadUri(String elfFileDownloadUri) {
        this.elfFileDownloadUri = elfFileDownloadUri;
    }

    public String getElfFileType() {
        return elfFileType;
    }

    public void setElfFileType(String elfFileType) {
        this.elfFileType = elfFileType;
    }

    public long getElfSize() {
        return elfSize;
    }

    public void setElfSize(long elfSize) {
        this.elfSize = elfSize;
    }
}
