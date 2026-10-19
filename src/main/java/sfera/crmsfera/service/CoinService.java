//package sfera.crmsfera.service;
//
//import sfera.crmsfera.dto.ApiResponse;
//import sfera.crmsfera.dto.request.ReqCoin;
//import sfera.crmsfera.dto.response.ResCoin;
//import sfera.crmsfera.entity.Coin;
//import sfera.crmsfera.entity.Student;
//import sfera.crmsfera.exception.DataNotFoundException;
//import lombok.RequiredArgsConstructor;
//import sfera.crmsfera.mapper.CoinMapper;
//import org.springframework.stereotype.Service;
//import sfera.crmsfera.repository.CoinRepository;
//import sfera.crmsfera.repository.StudentRepository;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CoinService {
//
//    private final StudentRepository studentRepository;
//    private final CoinRepository coinRepository;
//    private final CoinMapper coinMapper;
//
//    public ApiResponse<String> minusCoin(Long studentId, int coin){
//        Student student = studentRepository.findById(studentId).orElseThrow(
//                () -> new DataNotFoundException("Student not found")
//        );
//
//        student.setCoin(student.getCoin() - coin);
//        studentRepository.save(student);
//        return ApiResponse.success(null, "Success");
//    }
//
//
//    public ApiResponse<String> plusCoin(Long studentId, int coin){
//        Student student = studentRepository.findById(studentId).orElseThrow(
//                () -> new DataNotFoundException("Student not found")
//        );
//
//        student.setCoin(student.getCoin() + coin);
//        studentRepository.save(student);
//        return ApiResponse.success(null, "Success");
//    }
//
//
//    public ApiResponse<String> saveCoin(ReqCoin reqCoin){
//        Coin coin = Coin.builder()
//                .name(reqCoin.getName())
//                .description(reqCoin.getDescription())
//                .imgUrl(reqCoin.getImgUrl())
//                .coin(reqCoin.getCoin())
//                .build();
//        coinRepository.save(coin);
//        return ApiResponse.success(null, "Success");
//    }
//
//
//    public ApiResponse<String> updateCoin(ResCoin resCoin){
//        Coin coin = coinRepository.findById(resCoin.getId()).orElseThrow(
//                () -> new DataNotFoundException("Coin not found")
//        );
//
//        coin.setName(resCoin.getName());
//        coin.setDescription(resCoin.getDescription());
//        coin.setImgUrl(resCoin.getImgUrl());
//        coin.setCoin(resCoin.getCoin());
//        coinRepository.save(coin);
//        return ApiResponse.success(null, "Success");
//    }
//
//
//    public ApiResponse<String> deleteCoin(Long id){
//        Coin coin = coinRepository.findById(id).orElseThrow(
//                () -> new DataNotFoundException("Coin not found")
//        );
//        coinRepository.delete(coin);
//        return ApiResponse.success(null, "Success");
//    }
//
//
//    public ApiResponse<ResCoin> getCoinById(Long id) {
//        Coin coin = coinRepository.findById(id).orElseThrow(
//                () -> new DataNotFoundException("Coin not found")
//        );
//        return ApiResponse.success(coinMapper.resCoin(coin), "Success");
//    }
//
//
//    public ApiResponse<List<ResCoin>> getAllCoin(){
//        List<ResCoin> list = coinRepository.findAll().stream().map(coinMapper::resCoin).toList();
//        return ApiResponse.success(list, "Success");
//    }
//}
