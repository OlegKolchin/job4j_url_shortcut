package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "site")
public class Site {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(unique = true)
   private String name;

   @OneToOne(mappedBy = "site")
   private Person person;

   public static Site of(String name) {
      Site site = new Site();
      site.name = name;
      return site;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      Site site = (Site) o;
      return id == site.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }
}
