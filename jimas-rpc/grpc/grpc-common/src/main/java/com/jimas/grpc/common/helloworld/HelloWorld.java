// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HelloWorld.proto

package com.jimas.grpc.common.helloworld;

/**
 * @author liuqj
 */
public final class HelloWorld {
  private HelloWorld() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Request_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Request_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Response_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Response_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\020HelloWorld.proto\"C\n\007Request\022\014\n\004num1\030\001 " +
      "\001(\001\022\014\n\004num2\030\002 \001(\001\022\034\n\006opType\030\003 \001(\0162\014.Oper" +
      "ateType\"0\n\010Response\022\016\n\006result\030\001 \001(\001\022\024\n\014r" +
      "esponsePort\030\002 \001(\005*=\n\013OperateType\022\014\n\010Addi" +
      "tion\020\000\022\014\n\010Division\020\001\022\t\n\005Multi\020\002\022\007\n\003Sub\020\003" +
      "2+\n\007Operate\022 \n\tcalculate\022\010.Request\032\t.Res" +
      "ponseB$\n com.jimas.grpc.common.helloworl" +
      "dP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_Request_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Request_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Request_descriptor,
        new String[] { "Num1", "Num2", "OpType", });
    internal_static_Response_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_Response_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Response_descriptor,
        new String[] { "Result", "ResponsePort", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
