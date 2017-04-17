// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

// edited by Sarah Abowitz 
import java.util.Collection;
import java.util.LinkedList;

final class MyJSON implements JSON {
    
  private LinkedList<String> keys; // where we'll keep our names
  private LinkedList<String> strings; // strings
  private LinkedList<JSON> objects; // and objects
  
  //because of the way I'm encoding these locations, 
  //theoretically the largest object has 255 strings and 255 objects in there.
 
  public MyJSON() {
      //
    }
  
  public MyJSON(LinkedList<String> k, LinkedList<String> s, LinkedList<JSON> o) {
      keys = k;
      strings = s;
      objects = o;
      
    }
  
    // For get & set methods: I know you could use a hashtable for O(1) performance, but we only learned the theory
    // in class and I don't know how to code that yet
    
  @Override
  public JSON getObject(String name) {
    String index = "";
    name=name+"O";
    for (String k:keys) {
        if(k.startsWith(name)){
            index = k;
            break;
        }
    }
    char c = index.charAt(index.length()-1);
    int objIndex = Character.getNumericValue(c);
    int cnt = 1;
    for (JSON o: objects){
        if (cnt == objIndex){return o;}
        cnt++;
    }
    return null;
  }

  @Override
  public JSON setObject(String name, JSON value) {
    String index = "";
    name=name+"O";
    for (String k:keys) {
        if(k.startsWith(name)){
            index = k;
            break;
        }
    }
    char c = index.charAt(index.length()-1);
    int objIndex = Character.getNumericValue(c);
    int cnt = 1;
    for (JSON o: objects){
        if (cnt == objIndex){o = value; break;}
        cnt++;
    }
    return this;
  }

  @Override
  public String getString(String name) {
    String index = "";
    name = name+"S";
    for (String k:keys) {
        if(k.startsWith(name)){
            index = k;
            break;
        }
    }
    char c = index.charAt(index.length()-1);
    int strIndex = Character.getNumericValue(c);
    int cnt = 1;
    for (String s: strings){
        if (cnt == strIndex){return s;}
        cnt++;
    }
    return null;
  }

  @Override
  public JSON setString(String name, String value) {
    String index = "";
    name = name+"S";
    for (String k:keys) {
        if(k.startsWith(name)){
            index = k;
            break;
        }
    }
    char c = index.charAt(index.length()-1);
    int strIndex = Character.getNumericValue(c);
    int cnt = 1;
    for (String s: strings){
        if (cnt == strIndex){s = value; break;}
        cnt++;
    }
    return this;
  }

  @Override
  public void getObjects(Collection<String> names) {
    if (keys.size()>0){
          for (String k : keys) {
            if (k.indexOf("O") == k.length()-2) {names.add(k); }
        }
        if (names.size() == 0){
            names.add("no objects here");
        }
    }else {
        names.add("no objects here");
    }
  }

  @Override
  public void getStrings(Collection<String> names) {
    if (keys.size() > 0){
        for (String k : keys) {
            if (k.indexOf("S") == k.length()-2) {names.add(k); }
        }
        if (names.size() == 0){
            names.add("no strings here");
        }
    } else {
        names.add("no strings here");
    }
    
  }
}