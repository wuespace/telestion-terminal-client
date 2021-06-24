FROM openjdk:16.0

ENV WORK_DIR /usr/telestion
ENV IMAGE telestion-terminal-client

COPY build/distributions/$IMAGE.tar $WORK_DIR/

WORKDIR $WORK_DIR
ENTRYPOINT ["sh", "-c"]

CMD ["tar -xf $IMAGE.tar && cd $IMAGE && ./bin/$IMAGE"]
