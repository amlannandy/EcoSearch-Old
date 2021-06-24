import 'package:dio/dio.dart';
import 'package:ecosearch/data/api_abstract.dart';
import 'package:ecosearch/models/ApiResponse.dart';

class Api implements ApiAbstract {
  late Dio _dio;

  Api() {
    _dio = Dio();
    _dio.interceptors.clear();
  }

  @override
  Future<ApiResponse> login(String email, String password) {
    // TODO: implement login
    throw UnimplementedError();
  }

  @override
  Future<ApiResponse> register(
      String name, String email, String username, String password) {
    // TODO: implement register
    throw UnimplementedError();
  }
}
