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
import java.util.LinkedList;
import java.util.Stack;
import java.io.IOException;

final class MyJSONParser implements JSONParser {
  
  @Override
  public JSON parse(String in) throws IOException {
    int lastMark = 0, totalMarks = 0; 
    String temp = "";
    for (int i = 0; i< in.length(); i++){
        if (in.charAt(i)=='"'){
            totalMarks++;
            if (totalMarks % 2 != 0){
                String stripped = in.substring(lastMark+1,i);
                temp = temp+stripped;
            }             
            lastMark = i;
        }
        if(totalMarks % 2 == 0){
            if(i == in.length()-1){
                temp = temp+in.substring(i);
            } else {
                temp = temp+in.substring(i+1);
            }
        }
    }
    in = temp;
    
    int strCnt = 1, objCnt = 1;
    LinkedList<String> names = new LinkedList<String>();
    LinkedList<String> strings = new LinkedList<String>();
    LinkedList<JSON> objects = new LinkedList<JSON>();
    Boolean objectMode = false;
    int objectOpening = 0, closing = 0;
    
    while (in.length() > 0 ){
        int index = in.indexOf(':');
        int fQ = in.indexOf('"'); //fQ = first quotation mark
        String name = in.substring(fQ+1,index-1);
        
        if(objectMode) {
            Stack<String> s = new Stack<String>();
            s.push("{");
            int from = 1;
            int rBrac = 0;
            while (!s.empty()){
               int lBrac = in.indexOf(123,from);
               rBrac = in.indexOf(125, from);
               if(lBrac < rBrac) {
                   from = lBrac;
                   s.push("{");
                }
               if (rBrac < lBrac) {
                   from = rBrac;
                   s.pop();
                }
            }
            if (rBrac+1 == in.length()){
                objects.add(parse(in));
                in = "";
            }else{
                objects.add(parse(in.substring(0,rBrac+1)));
                in = in.substring(rBrac+1);
            }
            objectMode = false;
        }
        
        if (!objectMode){
            if (in.charAt(index+1) == '"'){
                char c = (char) strCnt;
                String suffix = "S"+Character.toString(c);
                name = name+suffix;
                names.add(name);
                strCnt++;
                in = in.substring(index+2);
                int lQ = in.indexOf('"');
                String string = in.substring(0,lQ);
                in = in.substring(lQ+1);
                
            } else if (in.charAt(index+1) == '{'){
                char c = (char) objCnt;
                String suffix = "O"+Character.toString(c);
                name = name+suffix;
                names.add(name);
                objCnt++;
                objectMode = true;
            }
        }
        
    }
    // do something with "freddie" : "fish"
    
    return new MyJSON(names, strings, objects);
  }
}