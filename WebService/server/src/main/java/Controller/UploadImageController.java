package Controller;

import com.google.gson.JsonObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
public class UploadImageController extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "uploads";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra nếu yêu cầu thực sự chứa tệp tải lên
        if (!ServletFileUpload.isMultipartContent(request)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Required Field Missing");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        // Cấu hình cài đặt tải lên
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Cấu hình bộ nhớ tối thiểu cần thiết
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // Cấu hình thư mục tạm thời
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // Cấu hình kích thước tối đa cho tệp tải lên
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // Cấu hình kích thước tối đa cho yêu cầu tải lên
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // Đường dẫn tuyệt đối đến thư mục tải lên
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        System.out.println(uploadPath);
        // Tạo thư mục nếu nó không tồn tại
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        System.out.println(fileName);
                        System.out.println(filePath);
                        // Lưu tệp trên ổ đĩa
                        item.write(storeFile);

                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        JsonObject jsonResponse = new JsonObject();
                        jsonResponse.addProperty("success", true);
                        jsonResponse.addProperty("message", "Successfully Uploaded");
                        jsonResponse.addProperty("name", fileName);
                        response.getWriter().write(jsonResponse.toString());
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Error while uploading");
            response.getWriter().write(jsonResponse.toString());
        }
    }
}
