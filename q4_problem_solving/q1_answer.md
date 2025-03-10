### ในกรณีที่ API ที่พัฒนามีปัญหาด้านประสิทธิภาพ เช่น
 - การตอบสนองของ API ช้ากว่าที่คาดไว้
 - การตอบสนองจากฐานข้อมูลมีความล่าช้า
 - มีการเรียกใช้งาน API ในปริมาณมาก (high throughput)

### คุณจะแก้ไขปัญหาเหล่านี้อย่างไรให้ API ทำงานได้อย่างมีประสิทธิภาพมากขึ้น?

Answer: 

หากปัญหามาจากการ query ที่ซับซ้อน เช่น การเรียกข้อมูลพร้อมกับการทำ join หลายๆ table โดยไม่จำเป็น 

หรือการต่อฐานข้อมูลหลายๆครั้ง ควรพิจารณาการปรับปรุง query เพื่อให้การ select ข้อมูลมีประสิทธิภาพ

และตรงกับการใช้งานมากที่สุด รวมถึงการลดจำนวนการเชื่อมต่อกับฐานข้อมูลให้เหลือน้อยที่สุด และ

อีกหนึ่งปัจจัยที่สำคัญคือการตั้งค่าการใช้งาน connection pool ให้เหมาะสมกับปริมาณการใช้งานจริง 

เพื่อให้การเชื่อมต่อ database เป็นไปอย่างมีประสิทธิภาพ และลดความล่าช้าที่เกิดจากการรอคิวเชื่อมต่อ

และยังมีการทำ caching ที่จะช่วยเพิ่มความเร็วในการตอบสนองสำหรับข้อมูลที่มีการเรียกใช้งานบ่อยๆและลดภาระของ database

แต่หากปัญหามาจากการที่ microservice รับโหลดมากเกินไปจากการเรียกใช้งาน API ในปริมาณสูง ควรพิจารณาการ Scale Out 

โดยการเพิ่มจำนวน pod ของ microservice เพื่อรองรับการทำงานในปริมาณที่มากขึ้น พร้อมใช้ load balancer 

ในการกระจาย request ไปยัง pod ต่างๆ โดยไม่ให้เซิร์ฟเวอร์ตัวเดียวถูก overload 

แต่หากมีข้อจำกัดในเรื่องของทรัพยากร เช่น แต่ละ request ต้องการการใช้ memory สูง อาจจำเป็นต้องพิจารณา Scale Up 

โดยการเพิ่มขนาด spec ของเครื่องเซิร์ฟเวอร์เพื่อเพิ่มความสามารถในการประมวลผล ทำให้สามารถรองรับการใช้งานที่ต้องการทรัพยากรมากขึ้นได้
