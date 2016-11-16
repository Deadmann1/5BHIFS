    @POST
    @Path("upload/{bookid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @PathParam("bookid") String id) throws Exception {
        Database db = Database.newInstance();
        Book retBook;
        String retString = "file uploaded";
        try {
            System.out.println("***********try to upload " + id + "/" + fileDetail.getFileName());
            retBook = db.getBook(Integer.parseInt(id));

            saveFile(uploadedInputStream, fileDetail);
            retBook.setNameOfImage(fileDetail.getFileName());
        } catch (Exception e) {
            System.out.println("***********errors in webservice: " + e.getMessage());
            retString = e.getMessage();
        }
        return Response.status(200).entity(retString).build();
    }

    @GET
    @Path("download/{bookid}")
    @Produces("image/jpg")
    public Response getFile(@PathParam("bookid") String id) {
        Database db = Database.newInstance();
        Book retBook;
        ResponseBuilder response = null;
        System.out.println("****************start of download - 1");
        try {
            retBook = db.getBook(Integer.parseInt(id));
            java.io.File file = new java.io.File("/tmp/" + retBook.getNameOfImage());

            System.out.println("*************start download of " + retBook.getNameOfImage());
            response = Response.ok((Object) file);
            response.header("Content-Disposition",
                    "attachment; filename=" + retBook.getNameOfImage());
        } catch (Exception ex) {
            System.out.println("***********error in webservice: " + ex.getMessage());
        }
        return response.build();

    }

    private void saveFile(InputStream uploadedInputStream,
            FormDataContentDisposition fileDetail) throws Exception {
        OutputStream os = null;
        File fileToUpload = new File("/tmp/" + fileDetail.getFileName());
        os = new FileOutputStream(fileToUpload);
        byte[] b = new byte[2048];
        int length;
        while ((length = uploadedInputStream.read(b)) != -1) {
            os.write(b, 0, length);
        }
        os.flush();
        os.close();

    }
