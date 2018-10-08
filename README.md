# artemis
Protótipo de loja virtual de replicas de obras de arte baseada na modalidade dropshipping. 

## Demonstração de uso
Vídeo demonstrando utilização do sistema em 4 cenários: https://youtu.be/YX_JNgZ5qdI

## Instruções para instalação e execução da aplicação (inglês)

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.
2. [Yarn][]: We use Yarn to manage Node dependencies.
   Depending on your system, you can install Yarn either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

    yarn install

We use [Gulp][] as our build system. Install the Gulp command-line tool globally with:

    yarn global add gulp-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    ./mvnw
    gulp

[Bower][] is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [bower.json](bower.json). You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.
