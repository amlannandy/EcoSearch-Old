class ApiResponse {
  bool? success;
  String? message;
  List<String>? errors;

  ApiResponse(Map<String, dynamic> json) {
    success = json['success'] ?? false;
    message = json['msg'] ?? null;
    if (json['errors'] != null) {
      errors =
          (json['errors'] as List<String>).map((e) => e.toString()).toList();
    }
  }
}
