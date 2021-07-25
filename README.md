# LiteBansAdditions
**LiteBansAdditions** is **Spigot** plugin which adds features to [**LiteBans**](https://www.spigotmc.org/resources/litebans.3715/).


**Features:**
- send punishment notifications on Discord using webhook
- apply effect to every player when punishment is executed
- play sound to every player when punishment is executed

## Usage
Put plugin jar in server's `/plugins` directory. Make sure, that **LiteBans** is also installed. You can configure the plugin in `config.yml` file. Use `/litebansadditions` (or `/lba`) command to reload the plugin (perm `litebansadditions.reload`).

## Issues/enhancements/questions
Please use [issues](https://github.com/Nikox3003/LiteBansAdditions/issues) section.

## Contributing
You can contribute to the project using **pull requests**. Make sure that you are working on the latest version of code.

## Bulding project
**Requirements**
- JDK 8 or newer
- Maven
- Git

**Compiling**
```bash
git clone https://github.com/Nikox3003/LiteBansAdditions.git
cd LiteBansAdditions
mvn clean package
```
Output jar can be found in `/target` directory.

## License
**LiteBansAdditions** is licensed under **MIT** license. For more details, check [LICENSE file](https://github.com/Nikox3003/LiteBansAdditions/blob/master/LICENSE)
