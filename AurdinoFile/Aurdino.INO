void setup() {
Serial.begin(9600);
 pinMode(4, OUTPUT); 
 pinMode(5, OUTPUT);
 pinMode(6, OUTPUT);
 pinMode(7, OUTPUT);
 pinMode(8, OUTPUT);
 pinMode(9, OUTPUT);
 pinMode(10, OUTPUT);
 pinMode(11, OUTPUT);
 pinMode(12, OUTPUT);// put your setup code here, to run once:
 }
 
void loop() {
  // put your main code here, to run repeatedly:
 if(Serial.available()>0)
   {     
      char data= Serial.read(); // reading the data received from the bluetooth module
      switch(data)
      {
        case 'f':  // when F is pressed on the app on Terminal
          digitalWrite(5, HIGH);
          delay(100);
          digitalWrite(5,LOW);
          digitalWrite(8,HIGH);
          delay(100);
          digitalWrite(8,LOW);
          digitalWrite(11,HIGH);
          delay(100);
          digitalWrite(11,LOW);
          digitalWrite(12,HIGH);
          delay(100);
          digitalWrite(12,LOW);
          digitalWrite(13,HIGH);
          delay(100);
          digitalWrite(13,LOW);
          break; 
        case 'm': // when M is pressed on the app on Terminal
          digitalWrite(5, HIGH);
          delay(100);
          digitalWrite(5,LOW);
          digitalWrite(8,HIGH);
          delay(100);
          digitalWrite(8,LOW);
          digitalWrite(11,HIGH);
          delay(100);
          digitalWrite(11,LOW);
          digitalWrite(9,HIGH);
          delay(100);
          digitalWrite(9,LOW);
          digitalWrite(13,HIGH);
          delay(100);
          digitalWrite(13,LOW);
          digitalWrite(10,HIGH);
          delay(100);
          digitalWrite(10,LOW);
          digitalWrite(7,HIGH);
          delay(100);
          digitalWrite(7,LOW);
          break;
        case 'r': // when R is pressed on the app on Terminal
          digitalWrite(5, HIGH);
          delay(100);
          digitalWrite(5,LOW);
          digitalWrite(8,HIGH);
          delay(100);
          digitalWrite(8,LOW);
          digitalWrite(11,HIGH);
          delay(100);
          digitalWrite(11,LOW);
          digitalWrite(12,HIGH);
          delay(100);
          digitalWrite(12,LOW);
          digitalWrite(13,HIGH);
          delay(100);
          digitalWrite(13,LOW);
          digitalWrite(10,HIGH);
          delay(100);
          digitalWrite(10,LOW);
          digitalWrite(7,HIGH);
          delay(100);
          digitalWrite(7,LOW);
          break;
        case 'l': // when L is pressed on the app on Terminal
          digitalWrite(11, HIGH);
          delay(100);
          digitalWrite(11,LOW);
          digitalWrite(8,HIGH);
          delay(100);
          digitalWrite(8,LOW);
          digitalWrite(5,HIGH);
          delay(100);
          digitalWrite(5,LOW);
          digitalWrite(6,HIGH);
          delay(100);
          digitalWrite(6,LOW);
          digitalWrite(7,HIGH);
          delay(100);
          digitalWrite(7,LOW);
          break;
        case '1': // when S is pressed on the app on Terminal
          digitalWrite(13, HIGH);
          delay(100);
          digitalWrite(13,LOW);
          digitalWrite(12,HIGH);
          delay(100);
          digitalWrite(12,LOW);
          digitalWrite(11,HIGH);
          delay(100);
          digitalWrite(11,LOW);
          digitalWrite(8,HIGH);
          delay(100);
          digitalWrite(8,LOW);
          digitalWrite(9,HIGH);
          delay(100);
          digitalWrite(9,LOW);
          digitalWrite(10,HIGH);
          delay(100);
          digitalWrite(10,LOW);
          digitalWrite(7,HIGH);
          delay(100);
          digitalWrite(7,LOW);
          digitalWrite(6,HIGH);
          delay(100);
          digitalWrite(6,LOW);
          digitalWrite(5,HIGH);
          delay(100);
          digitalWrite(5,LOW);
          break;  
        default :
          digitalWrite(13,LOW);
          digitalWrite(12,LOW);
          digitalWrite(11,LOW);
          digitalWrite(10,LOW);
          digitalWrite(9,LOW);
          digitalWrite(8,LOW);
          digitalWrite(7,LOW);
          digitalWrite(6,LOW);
          digitalWrite(5,LOW);          
          break;
      }
      Serial.println(data);
   }
   delay(50);
}
