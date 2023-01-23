<h1 align="center">
  <br/>
  <a href="https://github.com/DerEingerostete/BasicHomes"><img src="https://dl.dereingerostete.dev/preview?fileName=BasicHomesLogo.png" alt="BasicHomes" width="600"></a>
</h1>

___

<p align="center">
  <a href="#commands">Commands</a> •
  <a href="#config">Config</a> •
  <a href="#language-support">Language Support</a> •
  <a href="#download">Download</a> •
  <a href="#license">License</a>
</p>

## Commands
| Command     |  Description  |         Usage         | Permission            |
|:------------|:-------------:|:---------------------:|-----------------------|
| /home       | Opens the GUI | /home or /home <name> | basichomes.home       |
| /sethome    |  Sets a home  |    /sethome <name>    | basichomes.sethome    |
| /removehome | Remove a home |  /removehome <name>   | basichomes.removehome |

## Config
The config is very basic and only contains three settings
```json
{
    "language": "en",
    "databaseFile": "database.db",
    "maxHomes": 3
}
`````
### Definitions
`language`: The language file that should be selected (e.g. `en` for `en.json`)<br/>
`databaseFile`: The filename of the database<br/>
`maxHomes`: The max amount of homes a user can set<br/>

> **Warning**<br/>
> `maxHomes` should not be greater than 3, otherwise the GUI will break

## Language Support
To allow multiple languages a folder named `lang` is created at the first start, in which language configurations can be created.<br/>
These are selected with the `language` option in the `config.json`.

## Download
Currently, there is no resource on SpigotMC yet, so the plugin can currently only be downloaded via the [release tab](https://github.com/DerEingerostete/BasicHomes/releases).<br/>
The latest release can be found [here](https://github.com/DerEingerostete/BasicHomes/releases/latest).

## License
Distributed under the MPL 2.0 License. See [`LICENSE`](/LICENSE) for more information.
