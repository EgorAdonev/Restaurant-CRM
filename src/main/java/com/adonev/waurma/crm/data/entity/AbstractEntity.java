//package com.adonev.waurma.crm.data.entity;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//
////@MappedSuperclass
//public abstract class AbstractEntity {
//
//    @Id
//    @GeneratedValue
////    @Type(type = "long")
//    private Integer id;
////    @Id
////    @GeneratedValue
////    private UUID id;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    @Override
//    public int hashCode() {
//        if (id != null) {
//            return id.hashCode();
//        }
//        return super.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof AbstractEntity)) {
//            return false; // null or other class
//        }
//        AbstractEntity other = (AbstractEntity) obj;
//
//        if (id != null) {
//            return id.equals(other.id);
//        }
//        return super.equals(other);
//    }
//}
