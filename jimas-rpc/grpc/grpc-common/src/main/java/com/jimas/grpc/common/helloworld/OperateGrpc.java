package com.jimas.grpc.common.helloworld;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 *定义服务
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.57.2)",
    comments = "Source: HelloWorld.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OperateGrpc {

  private OperateGrpc() {}

  public static final String SERVICE_NAME = "Operate";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Request,
      Response> getCalculateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "calculate",
      requestType = Request.class,
      responseType = Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Request,
      Response> getCalculateMethod() {
    io.grpc.MethodDescriptor<Request, Response> getCalculateMethod;
    if ((getCalculateMethod = OperateGrpc.getCalculateMethod) == null) {
      synchronized (OperateGrpc.class) {
        if ((getCalculateMethod = OperateGrpc.getCalculateMethod) == null) {
          OperateGrpc.getCalculateMethod = getCalculateMethod =
              io.grpc.MethodDescriptor.<Request, Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "calculate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Response.getDefaultInstance()))
              .setSchemaDescriptor(new OperateMethodDescriptorSupplier("calculate"))
              .build();
        }
      }
    }
    return getCalculateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OperateStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OperateStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OperateStub>() {
        @Override
        public OperateStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OperateStub(channel, callOptions);
        }
      };
    return OperateStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OperateBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OperateBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OperateBlockingStub>() {
        @Override
        public OperateBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OperateBlockingStub(channel, callOptions);
        }
      };
    return OperateBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OperateFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OperateFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OperateFutureStub>() {
        @Override
        public OperateFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OperateFutureStub(channel, callOptions);
        }
      };
    return OperateFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *定义服务
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void calculate(Request request,
                           io.grpc.stub.StreamObserver<Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCalculateMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Operate.
   * <pre>
   *定义服务
   * </pre>
   */
  public static abstract class OperateImplBase
      implements io.grpc.BindableService, AsyncService {

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return OperateGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Operate.
   * <pre>
   *定义服务
   * </pre>
   */
  public static final class OperateStub
      extends io.grpc.stub.AbstractAsyncStub<OperateStub> {
    private OperateStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OperateStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OperateStub(channel, callOptions);
    }

    /**
     */
    public void calculate(Request request,
                          io.grpc.stub.StreamObserver<Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCalculateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Operate.
   * <pre>
   *定义服务
   * </pre>
   */
  public static final class OperateBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<OperateBlockingStub> {
    private OperateBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OperateBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OperateBlockingStub(channel, callOptions);
    }

    /**
     */
    public Response calculate(Request request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCalculateMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Operate.
   * <pre>
   *定义服务
   * </pre>
   */
  public static final class OperateFutureStub
      extends io.grpc.stub.AbstractFutureStub<OperateFutureStub> {
    private OperateFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected OperateFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OperateFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Response> calculate(
        Request request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCalculateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CALCULATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CALCULATE:
          serviceImpl.calculate((Request) request,
              (io.grpc.stub.StreamObserver<Response>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCalculateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              Request,
              Response>(
                service, METHODID_CALCULATE)))
        .build();
  }

  private static abstract class OperateBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OperateBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return HelloWorld.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Operate");
    }
  }

  private static final class OperateFileDescriptorSupplier
      extends OperateBaseDescriptorSupplier {
    OperateFileDescriptorSupplier() {}
  }

  private static final class OperateMethodDescriptorSupplier
      extends OperateBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OperateMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OperateGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OperateFileDescriptorSupplier())
              .addMethod(getCalculateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
