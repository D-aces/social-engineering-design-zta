package main.java.com.otu2.zt_engine.controller;

// Serves endpoints 
@Controller
@RequestMapping(value = "/eval-access")
public class AccessController {
    @Autowired
     private EvaluationService evaluationService;

     @PostMapping
      public ResponseEntity<AccessDecision> evaluateAccess(@Valid @RequestBody AccessRequest request) {
         AccessDecision decision = evaluationService.evaluateAccess(request);
         return ResponseEntity.ok(decision);
    }

}
