package com.cashmanager.services.mapper;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseTemplate {

  static public ResponseEntity<?> badRequest(String error) {
    HashMap<String, Object> map = new HashMap<>();

    if (error == null) {
      map.put("message", "You can't access this resource.");
      return ResponseEntity.status(401).body(map);
    }
    map.put("message", error);
    return ResponseEntity.badRequest().body(map);
  }

  static public ResponseEntity<?> deleted() {
    HashMap<String, Object> map = new HashMap<>();

    map.put("message", "deleted");
    return ResponseEntity.ok().body(map);
  }

  static public ResponseEntity<?> alreadyExists() {
    HashMap<String, Object> map = new HashMap<>();

    map.put("message", "already exists");
    return ResponseEntity.status(409).body(map);
  }

  static public ResponseEntity<?> success(Object data) {
    return ResponseEntity.ok().body(data);
  }

  static public ResponseEntity<?> notFound() {
    HashMap<String, Object> map = new HashMap<>();

    map.put("message", "not found");
    return ResponseEntity.status(404).body(map);
  }

  static public ResponseEntity<?> created(Object value) {
    return ResponseEntity.status(201).body(value);
  }
}