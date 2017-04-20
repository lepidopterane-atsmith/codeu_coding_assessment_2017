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
  private boolean isEmpty;
  
  
  public MyJSON() {
      isEmpty = true;
    }
  
  public MyJSON(LinkedList<String> k, LinkedList<String> s, LinkedList<JSON> o) {
      keys = k;
      strings = s;
      objects = o;
      isEmpty = false;
    }
  
    // For get & set methods: I know you could use a hashmap for O(1) performance, but we only learned the theory
    // in class and I don't have the time to redesign the whole class & parser for that, sorry
    
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
    int objIndex = Character.getNumericValue(c)-31;
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
    int strIndex = Character.getNumericValue(c)-31;
    int cnt = 1;
    System.out.println("Strings include:");
    for (String s: strings){
        System.out.println(s);
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
    LinkedList<String> preNames = new LinkedList<String>();
      if (!isEmpty){
          for (String k : keys) {
            if (k.indexOf("O") == k.length()-2) {preNames.add(k); }
        }
        if (names.size() == 0){
            //
        }
    }else {
        //
    }
    names.clear();
    names.addAll(preNames);
  }

  @Override
  public void getStrings(Collection<String> names) {
    LinkedList<String> preNames = new LinkedList<String>();
    
      if (!isEmpty){
        for (String k : keys) {
            if (k.indexOf("S") == k.length()-2) {preNames.add(k); }
        }
        if (names.size() == 0){
            //
        }
    } else {
        //
    }
    names.clear();
    names.addAll(preNames);
  }
}