package sfera.crmsfera.controller;

//@RestController
//@RequestMapping("/option")
//@RequiredArgsConstructor
//public class OptionController {
//    private final OptionService optionService;
//
//    @PostMapping("/{questionId}")
//    public ResponseEntity<ApiResponse<String>> addOption(@PathVariable Long questionId, @RequestBody ReqOption req) {
//      return ResponseEntity.ok(optionService.createOption(questionId,req));
//    }
//
//    @GetMapping("/{questionId}")
//    public ResponseEntity<ApiResponse<ResPageable>> getOptionsByPage(@PathVariable Long questionId,
//                                                                     @RequestParam(defaultValue = "0") int page,
//                                                                     @RequestParam(defaultValue = "10") int size) {
//      return ResponseEntity.ok(optionService.getOptionsByQuestionIdByPage(questionId,page,size));
//    }
//
//    //public ResponseEntity
//
//}
