// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HelloWorld.proto

package com.jimas.grpc.common.helloworld;

public interface RequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Request)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>double num1 = 1;</code>
   * @return The num1.
   */
  double getNum1();

  /**
   * <code>double num2 = 2;</code>
   * @return The num2.
   */
  double getNum2();

  /**
   * <code>.OperateType opType = 3;</code>
   * @return The enum numeric value on the wire for opType.
   */
  int getOpTypeValue();
  /**
   * <code>.OperateType opType = 3;</code>
   * @return The opType.
   */
  OperateType getOpType();
}