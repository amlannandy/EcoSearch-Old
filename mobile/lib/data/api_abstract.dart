import 'package:ecosearch/models/ApiResponse.dart';

abstract class ApiAbstract {
  // Auth
  Future<ApiResponse> login(String email, String password);
  Future<ApiResponse> register(
      String name, String email, String username, String password);
}
