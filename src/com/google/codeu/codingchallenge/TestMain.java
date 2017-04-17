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

// edited by Sarah Abowitz. 
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

final class TestMain {

  public static void main(String[] args) {

    final Tester tests = new Tester();

    tests.add("Empty Object", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        
        final JSON obj = parser.parse("{ }");
        
        final Collection<String> strings = new HashSet<>();
        
        obj.getStrings(strings); //I know what you want here but I'm not sure how to fix it
        
        Asserts.isEqual(strings.size(), 0);
        
        final Collection<String> objects = new HashSet<>();
        obj.getObjects(objects);

        Asserts.isEqual(objects.size(), 0);
      }
    });

    tests.add("String Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {
        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":\"sam doe\" }"); //why you no parse

        Asserts.isEqual("sam doe", obj.getString("name"));
        
       // error: String index out of range -3
     }
    });

    tests.add("Object Value", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {

        final JSONParser parser = factory.parser();
        final JSON obj = parser.parse("{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" } }");

        final JSON nameObj = obj.getObject("name");

        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first"));
        Asserts.isEqual("doe", nameObj.getString("last"));
        //ok, this one just crashes with an out-of-memory error
      }
    });

    /* I've commented this test out bc it's the one I wanted to ultimately use but bc of debugging troubles I couldn't
     * utilize this to its fullest.
     * 
     * tests.add("TAZ Pizza", new Test() {
      @Override
      public void run(JSONFactory factory) throws Exception {

        final JSONParser parser = factory.parser();
        
        //This is a complex test case I developed to test what TestMain couldn't initially.
        //It's called TAZ Pizza because it's an object containing various pizza orders from characters in 
        //The Adventure Zone
        final JSON obj = parser.parse("{\"Merle\":\"plain\"},\"Angus\":{\"half1\":\"bacon\", \"half2\":\"extra-cheese\"},\"Magnus\":\"meatball\", \"Lucretia\":{\"pizza1\":\"buffalo-chicken\",\"pizza2\":\"onion\",\"pizza3\":\"olive\"},\"Taako\":\"pepperoni\"}");
        
        System.out.println(obj.getString("Merle"));
        
        final JSON nameObj = obj.getObject("name");
        
        Asserts.isNotNull(nameObj);
        Asserts.isEqual("sam", nameObj.getString("first"));
        Asserts.isEqual("doe", nameObj.getString("last"));
      }
    });*/
    
    
    
    tests.run(new JSONFactory(){
      @Override
      public JSONParser parser() {
        return new MyJSONParser();
      }

      @Override
      public JSON object() {
        return new MyJSON();
      }
    }); 
  }
}